package server.command.execute

import execute.packets.ExecutePacket
import product.Product
import server.command.execute.ClientCommand

class TestNewCommand(executePacket: ExecutePacket) : ClientCommand(executePacket) {

    val productId = executePacket.listOfNumberArgs!![0] as Int
    val product = executePacket.product as Product


    override val name: String = "test"

    override fun execute(): String {
        return "test succeed"
    }
}