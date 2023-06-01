package server.collection


import product.Product
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class ProductCollection {
    var products: TreeMap<Int, Product> = TreeMap()

    val creationDate: String = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))

}