package server.command.execute


import execute.packets.ExecutePacket
import product.Product


class Insert(executePacket: ExecutePacket) : ClientCommand(executePacket) {

    override val name: String = "insert"

    private val productId = executePacket.listOfNumberArgs!![0] as Int
    private val product = executePacket.product as Product

    companion object{
        const val info: String = "Adds a product to the collection"
    }


    override fun execute(): String {
        return if (!productCollection.products.containsKey(productId)) {
            productCollection.products[productId] = this.product
            "Product with id ($productId) was successfully added"
        } else {
            "The product with id($productId) already exist"
        }
    }
}