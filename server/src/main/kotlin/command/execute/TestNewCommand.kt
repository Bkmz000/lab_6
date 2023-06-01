package server.command.execute

import execute.packets.ExecutePacket
import product.Product

class TestNewCommand(executePacket: ExecutePacket) : ClientCommand(executePacket) {

    val productId = executePacket.listOfIntArgs!![0] as Int
    val product = executePacket.product as Product


    override val name: String = "test"

    override fun execute(): String {
        return "test succeed"
    }
}