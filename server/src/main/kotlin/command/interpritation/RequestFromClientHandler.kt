package server.command.interpritation

import PasswordUtils
import collection.TokensForUser
import collection.Users
import com.google.gson.GsonBuilder
import command.execute.AllExecuteCommands.samples
import execute.packets.ExecutePacket
import request.RequestPacket
import request.RequestType.*
import server.command.execute.builders.ExecuteCommandBuilder
import server.command.invoke.ExecuteInvoker

object RequestFromClientHandler {

    private val gson = GsonBuilder().create()



    fun interpretationRequestAndGetResult(message: String) : RequestPacket {
        val requestPacket = message.tryDecodeToRequestPacket()
            ?: return RequestPacket(COMMAND_EXECUTE, message = "Server error: Problem with file, please try again")

        val typeOfRequest = requestPacket.requestType
        val executePacket = requestPacket.executePacket

        val resultOfRequest = when(typeOfRequest){
            COMMAND_EXECUTE -> {
                val result = invokeExecuteCommandAndGetResult(executePacket!!)
                RequestPacket(COMMAND_EXECUTE, message = result)
            }
            REFRESH_SAMPLES_INFORMATION -> {
                RequestPacket(REFRESH_SAMPLES_INFORMATION, executeSamples =  samples)
            }
            LOGIN -> {
                val result = createTokenOrFailureLogin(requestPacket)
                RequestPacket(LOGIN, message = result, login = requestPacket.login)
            }
        }

        return resultOfRequest

    }

    private fun String.tryDecodeToRequestPacket() : RequestPacket? {
        return try {
            gson.fromJson(this, RequestPacket::class.java)
        } catch (e: Exception){
            null
        }
    }

    private fun createTokenOrFailureLogin(requestPacket: RequestPacket) : String?{
        val loginFromUser = requestPacket.login
        val passFromUser = requestPacket.pass
        if (Users.loginPass.containsKey(loginFromUser)) {
            if(PasswordUtils.isExpectedPassword(passFromUser!!, Users.loginPass[loginFromUser]!!)) {
                val randomToken = (0..10000000).random().toString()
                TokensForUser.userTokens[randomToken] = requestPacket.login!!
                return randomToken.toString()
            }
        }
        return null
    }

    private fun invokeExecuteCommandAndGetResult(executePacket: ExecutePacket) : String {
        val executeCommand = ExecuteCommandBuilder.getExecuteCommand(executePacket)
            ?: return "Server error: Cannot find the command"
        return  ExecuteInvoker.executeCommand(executeCommand)
    }


}