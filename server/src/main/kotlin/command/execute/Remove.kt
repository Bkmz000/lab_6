package server.command.execute

import collection.TokensForUser
import execute.packets.ExecutePacket

class Remove(executePacket: ExecutePacket) : ClientCommand(executePacket) {

    override val name: String = "remove"

    private val productId = executePacket.listOfIntArgs!![0]

    override fun execute(): String {
        val productFromCollection = productCollection.products[productId]
        val ownerName = TokensForUser.userTokens[executePacket.token]
        return if (productCollection.products.containsKey(productId)) {
            if(productFromCollection!!.owner == ownerName) {
                productCollection.products.remove(productId)
                "Product with id ($productId) was successfully removed"
            } else {
             "You don't have permission to update this product"
            }
        } else {
            "No such element with id = $productId"
        }
    }

}