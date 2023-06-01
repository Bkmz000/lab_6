package server.command.execute

import execute.packets.ExecutePacket

class Remove(executePacket: ExecutePacket) : ClientCommand(executePacket) {

    override val name: String = "remove"

    private val productId = executePacket.listOfNumberArgs!![0] as Int

    override fun execute(): String {
        return if (productCollection.products.containsKey(productId)) {
            productCollection.products.remove(productId)
            "Product with id ($productId) was successfully removed"
        } else {
            "No such element with id = $productId"
        }
    }

}