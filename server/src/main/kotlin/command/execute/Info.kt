package server.command.execute

import execute.packets.ExecutePacket
import org.koin.core.component.KoinComponent

class Info(executePacket: ExecutePacket) : ClientCommand(executePacket), KoinComponent {

    override val name: String = "info"


    private val collectionType = productCollection.products::class.toString()
    private val creationDate = productCollection.creationDate
    private val numberOfElements = productCollection.products.size

    override fun execute(): String {

        return this.toString()


    }

    override fun toString(): String {
        return "CollectionType = '${collectionType.substringAfter("util.")}', CreationDate = '$creationDate', Number of elements = $numberOfElements"
    }




}