package collection

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.Test
import org.postgresql.ds.PGSimpleDataSource

class DatabaseTest {

    @Test
    fun connect() {

        val dataSource = PGSimpleDataSource().apply {
            user = "postgres"
            password = "1234"
            databaseName = "products"
        }

        val database = Database.connect(dataSource)

        transaction {

            SchemaUtils.setSchema(Schema("public"))
            val res = productsFromDataBase.selectAll()
            res.forEach {
                println(it)
            }
        }

    }

}

object products: Table("products_full") {
    val productId = integer("product_id")
    val productName = text("product_name")
    val coordinateX = integer("coordinate_x")
    val coordinateY = double("coordinate_y")
    val creationDate = text("creationdate")
    val price = integer("price")
    val unit_of_measure = text("unit_of_measure")
    val manufacturer_name = text("manufacturer_name")
    val full_name = text("full_name")
    val organization_type = text("organization_type")
    val ownerName = text("owner_name")
}