package server.command.execute

import execute.packets.ExecutePacket
import org.koin.core.component.inject
import server.command.invoke.CommandInvoker

class History(executePacket: ExecutePacket) : ClientCommand(executePacket) {

    override val name: String = "history"

    private val commandInvoker by inject<CommandInvoker>()

    override fun execute(): String {
        val listOfCommands = ArrayList<String>()
        commandInvoker.commands.forEach {
            listOfCommands.add(it.name)
        }

        return if (listOfCommands.isNotEmpty()) {
            val result = "Last 5 commands : $listOfCommands"
            result
        } else {
            "You haven't entered any commands yet"
        }
    }

}