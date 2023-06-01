package server.command.execute

import execute.packets.ExecutePacket

class RemoveGreaterKey(executePacket: ExecutePacket) : ClientCommand(executePacket) {

    override val name: String = "remove_greater_key"

    private val productId = executePacket.listOfIntArgs!![0]

    override fun execute(): String {
        return if(productCollection.products.entries.removeAll { it.key > productId }){
            "Products were successfully removed"
        } else {
            "No such elements"
        }
    }

}