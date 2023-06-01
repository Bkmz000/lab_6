package server.command.execute.builders

import execute.packets.ExecutePacket
import org.koin.core.component.KoinComponent
import server.command.execute.ClientCommand
import server.command.execute.samples.AllExecuteCommands
import kotlin.reflect.KFunction
import kotlin.reflect.full.primaryConstructor

object ExecuteCommandBuilder : KoinComponent {


    fun getExecuteCommand(executePacket: ExecutePacket): ClientCommand? {
        val commandConstructor = getInstanceOfCommandConstructor(executePacket.commandName) ?: return null
        val command = commandConstructor.call(executePacket)

        return command as ClientCommand

    }


    private fun getInstanceOfCommandConstructor(nameOfCommand : String): KFunction<Any>?{
            val commandKClass = AllExecuteCommands.classesWithNames[nameOfCommand] ?: return null
            return commandKClass.primaryConstructor
    }


}