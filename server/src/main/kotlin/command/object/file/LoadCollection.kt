package server.command.`object`.file


import com.google.gson.GsonBuilder
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.jsonObject
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import product.Product
import server.collection.ProductCollection
import java.util.*

object LoadCollection : KoinComponent {

    private val file = FileCollection.getFile()
    private val collectionOfProducts by inject<ProductCollection>()
    private val gson = GsonBuilder().create()

    fun load(): String {
        return if(file != null) {

//            val textFromFile = file.readText()
//            val map = gson.fromJson<TreeMap<Int,Product>>(textFromFile, TreeMap::class.java)
//            collectionOfProducts.products = TreeMap(map)
//            println(collectionOfProducts.products)
//            //
            val jsonText = file.readText()
            collectionOfProducts.products.clear()
            val textToSortedMap = Json.decodeFromString<JsonElement>(jsonText).jsonObject.toSortedMap()
            addElementsFromMapToProductCollection(textToSortedMap)
            "Collection was loaded"
        } else {
            "File with collection was not found"
        }
    }

    private fun addElementsFromMapToProductCollection(givenSortedMap: SortedMap<String, JsonElement>){
        givenSortedMap.forEach {
            val key = it.key.toInt()
            val value = Json.decodeFromJsonElement<Product>(it.value)
            collectionOfProducts.products[key] = value
        }
    }
}