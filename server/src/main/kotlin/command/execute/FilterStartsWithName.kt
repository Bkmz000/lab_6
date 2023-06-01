package server.command.execute

import execute.packets.ExecutePacket

class FilterStartsWithName(executePacket: ExecutePacket) : ClientCommand(executePacket) {

    override val name: String = "filter_starts_with_name"

    private val subName: String = executePacket.listOfStringArgs!![0]


    override fun execute(): String {
        val products = productCollection.products.entries.filter { it.value.name.startsWith(subName) }
        return if (products.isNotEmpty()){
            products.toString()
        } else {
            "No such elements"
        }
    }
}