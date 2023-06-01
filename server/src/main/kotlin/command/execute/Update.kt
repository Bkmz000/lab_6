package server.command.execute

import execute.packets.ExecutePacket
import product.Product


class Update(executePacket: ExecutePacket) : ClientCommand(executePacket) {

    override val name: String = "update"

    private val productId = executePacket.listOfNumberArgs!![0] as Int
    private val product = executePacket.product as Product

    companion object{
        const val info = "Updates the product with the given id"
    }


    override fun execute(): String {
        return if (productCollection.products.containsKey(productId)) {
            productCollection.products.replace(productId, product)
            "Product was successfully updated"
        } else { "There is no element with id $productId" }
    }
}