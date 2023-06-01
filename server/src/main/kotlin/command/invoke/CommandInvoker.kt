package server.command.invoke

import org.koin.core.component.KoinComponent
import server.command.execute.ClientCommand

object CommandInvoker : KoinComponent {
    val commands = mutableListOf<ClientCommand>()

    fun executeCommand(command: ClientCommand) : String{
        commands.add(command)
        if(commands.size >= 5) commands.removeFirst()

        return command.execute()
    }


}