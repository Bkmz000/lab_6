package server.command.interpritation

import com.google.gson.GsonBuilder
import execute.packets.ExecutePacket
import execute.packets.RequestPacket
import execute.packets.RequestType.COMMAND_EXECUTE
import server.command.execute.builders.ExecuteCommandBuilder
import server.command.invoke.CommandInvoker

object CommandManager {

    private val gson = GsonBuilder().create()



    fun interpretationRequestAndGetResult(message: String) : RequestPacket {
        val requestPacket = message.tryDecodeToRequestPacket()
            ?: return RequestPacket(COMMAND_EXECUTE, message = "Server error: Problem with file, please try again")

        val typeOfRequest = requestPacket.requestType
        val executePacket = requestPacket.executePacket
        val argumentsOfRequest = requestPacket.executeSamples

        val resultOfRequest = when(typeOfRequest){
            COMMAND_EXECUTE -> {
                val result = invokeExecuteCommandAndGetResult(executePacket!!)
                RequestPacket(COMMAND_EXECUTE, message = result)
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

    private fun invokeExecuteCommandAndGetResult(executePacket: ExecutePacket) : String {
        val executeCommand = ExecuteCommandBuilder.getExecuteCommand(executePacket)
            ?: return "Server error: Cannot find the command"
        return  CommandInvoker.executeCommand(executeCommand)
    }


}