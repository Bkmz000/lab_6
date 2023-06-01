package server.command.execute

import execute.packets.ExecutePacket
import kotlinx.serialization.builtins.MapSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.json.Json
import product.Product
import server.command.`object`.file.FileCollection

class Save(executePacket: ExecutePacket) : ClientCommand(executePacket) {

    override val name: String = "save"

    private val file = FileCollection.getFile()

    private val json = Json { prettyPrint = true }

    override fun execute(): String {

        return if (file != null){
            val collectionJson = json.encodeToString(MapSerializer(Int.serializer(), Product.serializer()),productCollection.products)
            file.writeText(collectionJson)
            "Collection was successfully saved"
        } else
            "File with collection was not found"


    }
}