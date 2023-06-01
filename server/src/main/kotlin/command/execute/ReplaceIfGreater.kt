package server.command.execute

import execute.packets.ExecutePacket

class ReplaceIfGreater(executePacket: ExecutePacket) : ClientCommand(executePacket) {

    override val name: String = "replace_if_greater"

    private val productId = executePacket.listOfNumberArgs!![0] as Int
    private val price: Int = executePacket.listOfNumberArgs!![1] as Int

    override fun execute(): String {
        return if(productCollection.products.containsKey(productId)){
            val priceOfProduct = productCollection.products[productId]?.price
            if(priceOfProduct!! < this.price){
                val newProduct = null//ProductBuilderCLI().build()
                "Unable to add"
            } else "The price ($price) is not greater then $priceOfProduct"
        } else "No such element"
    }
}