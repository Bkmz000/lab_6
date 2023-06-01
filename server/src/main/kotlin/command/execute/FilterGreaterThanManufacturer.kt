package server.command.execute

import execute.packets.ExecutePacket

class FilterGreaterThanManufacturer(executePacket: ExecutePacket) : ClientCommand(
    executePacket
) {

    override val name: String = "filter_greater_than_manufacturer"

    private val nameOfManufacturer: String = executePacket.listOfStringArgs!![0]


    override fun execute(): String {
        val manufactures = productCollection.products.entries.filter {
            it.value.manufacturer.name.length > this.nameOfManufacturer.length }
        return if(manufactures.isNotEmpty()){
            manufactures.toString()
        } else {
            "No such elements"
        }
    }
}