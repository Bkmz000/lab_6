package server.command.execute

import execute.packets.ExecutePacket

class Clear(executePacket: ExecutePacket) : ClientCommand(executePacket) {

    override val name: String = "clear"

    override fun execute(): String {
        productCollection.products.clear()
        return "The collection is now empty"
    }

}