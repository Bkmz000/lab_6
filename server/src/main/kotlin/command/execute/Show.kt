package server.command.execute

import com.google.gson.GsonBuilder
import execute.packets.ExecutePacket
import org.koin.core.component.KoinComponent

class Show(executePacket: ExecutePacket) : ClientCommand(executePacket), KoinComponent {

    override val name: String = "show"

    private val gson = GsonBuilder().create()

    companion object{
        const val info = "Displays the list of all products in the collection"
    }


    override fun execute(): String {

        return if(productCollection.products.isNotEmpty()){
            val productsAsString = productCollection.products.toList().joinToString {
                "\n" + it.toString()
            }

           productsAsString
        } else {
            "The collection is empty"
        }
    }
}