package server.command.execute

import execute.packets.ExecutePacket

class MaxByCoordinates(executePacket: ExecutePacket) : ClientCommand(executePacket) {

    override val name: String = "max_by_coordinates"

    override fun execute(): String {

        return if (productCollection.products.isNotEmpty()) {
            val productWithMaxCoordinates =
                productCollection.products.values.maxBy { it.coordinates.x + it.coordinates.y }
                productWithMaxCoordinates.toString()
        } else {
            "The collection is empty"
        }
    }
}