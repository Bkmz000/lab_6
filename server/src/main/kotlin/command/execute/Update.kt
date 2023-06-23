package server.command.execute

import collection.TokensForUser
import collection.Users
import execute.packets.ExecutePacket
import product.Product


class Update(executePacket: ExecutePacket) : ClientCommand(executePacket) {

    override val name: String = "update"

    private val productId = executePacket.listOfIntArgs!![0]
    private val product = executePacket.product as Product

    companion object{
        const val info = "Updates the product with the given id"
    }


    override fun execute(): String {
        val productFromCollection = productCollection.products[productId]
        val ownerName = TokensForUser.userTokens[executePacket.token]
        return if (productFromCollection != null) {
            if(productFromCollection.owner == ownerName){
                productCollection.products.replace(productId, product)
                "Product was successfully updated"
            } else {
             "You don't have permission to update this product"
            }
        } else { "There is no element with id $productId" }
    }
}