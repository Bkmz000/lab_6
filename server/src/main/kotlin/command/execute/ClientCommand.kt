package server.command.execute

import execute.packets.ExecutePacket
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import server.collection.ProductCollection

abstract class ClientCommand(val executePacket: ExecutePacket) : KoinComponent {

    val productCollection by inject<ProductCollection>()

    abstract val name: String
    abstract fun execute(): String


}