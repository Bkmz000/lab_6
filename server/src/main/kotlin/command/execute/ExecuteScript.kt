package server.command.execute

import execute.packets.ExecutePacket
import org.koin.core.component.KoinComponent
import server.command.execute.builders.ExecuteCommandBuilder
import server.command.interpritation.RequestFromClientHandler
import java.lang.StringBuilder

class ExecuteScript(executePacket: ExecutePacket) : ClientCommand(executePacket), KoinComponent {

    override val name: String = "execute_script"


    //execute_script C:/itmo/labs/kotlin/lab5/script1.txt


    override fun execute(): String {
        val executePackets = executePacket.listOfExecutePacketsForScript!!

        if (executePackets.isEmpty()) return "The file is empty"


        val result = mutableListOf<String>()
            while (executePackets.isNotEmpty()) {

                val clientCommand =
                    ExecuteCommandBuilder.getExecuteCommand(executePackets.removeFirst())
                        ?: return "Server error: Cannot find the command"
                val executionResult = clientCommand.execute()
                result.add("\n$executionResult")
            }


        return result.toString()
    }

}

