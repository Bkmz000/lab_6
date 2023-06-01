package server.command.execute

import execute.packets.ExecutePacket
import kotlin.system.exitProcess

class Exit(executePacket: ExecutePacket) : ClientCommand(executePacket) {

    override val name: String = "exit"

    override fun execute(): String {
        exitProcess(0)
    }
}