package collection

import collection.productsFromDataBase.coordinateX
import collection.productsFromDataBase.coordinateY
import collection.productsFromDataBase.creationDate
import collection.productsFromDataBase.full_name
import collection.productsFromDataBase.manufacturer_name
import collection.productsFromDataBase.organization_type
import collection.productsFromDataBase.ownerName
import collection.productsFromDataBase.price
import collection.productsFromDataBase.productId
import collection.productsFromDataBase.productName
import collection.productsFromDataBase.unit_of_measure
import collection.usersFromDataBase.name
import collection.usersFromDataBase.pass
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.transaction
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

import org.postgresql.ds.PGSimpleDataSource
import product.*
import server.collection.ProductCollection

object Database: KoinComponent {

    private val productCollection by inject<ProductCollection>()

    val dataSource = PGSimpleDataSource().apply {
        user = "postgres"
        password = "1234"
        databaseName = "products"
    }

    private val database = Database.connect(dataSource)
    private val currentSchema = Schema("public")



    fun loadCollections(): String  {
        loadUsersToApp()
        loadProductCollectionToApp()
        return "The collection was loaded"
    }


    fun loadProductCollectionToApp(): String {
        transaction(database) {
            SchemaUtils.setSchema(currentSchema)
            val requestResult = productsFromDataBase.selectAll()

            var currentProduct: Product?
            requestResult.forEach {
                currentProduct = buildProductFromResultRaw(it)
                productCollection.products[it[productId]] = currentProduct!!

            }
        }

        return "The collection was loaded"
    }

    private fun loadUsersToApp(): String {
        transaction(database) {
            SchemaUtils.setSchema(currentSchema)
            val requestResult = usersFromDataBase.selectAll()

            requestResult.forEach {
                Users.loginPass[it[name]] = it[pass]
            }

        }

        return "Success"
    }

    fun loadProductCollectionToDatabase(): String {
        transaction(database) {
            SchemaUtils.setSchema(currentSchema)
            var currentProduct: Product
            var currentKey: Int
            productsFromDataBase.deleteAll()
            productCollection.products.forEach { that ->
                currentProduct = that.value
                currentKey = that.key

                productsFromDataBase.insert {
                    it[productId] = currentKey
                    it[productName] = currentProduct.name
                    it[coordinateX] = currentProduct.coordinates.x
                    it[coordinateY] = currentProduct.coordinates.y
                    it[creationDate] = currentProduct.creationDate
                    it[price] = currentProduct.price
                    it[unit_of_measure] = currentProduct.unitOfMeasure.toString()
                    it[manufacturer_name] = currentProduct.manufacturer.name
                    it[full_name] = currentProduct.manufacturer.fullName
                    it[organization_type] = currentProduct.manufacturer.type.toString()
                    it[ownerName] = currentProduct.owner
                }
            }
        }
        return "Success"
    }

    private fun buildProductFromResultRaw(resultRaw: ResultRow) : Product? {

        try {
            return Product.Builder()
                .name(resultRaw[productName])
                .coordinates(Coordinates(resultRaw[coordinateX], resultRaw[coordinateY]))
                .creationDate(resultRaw[creationDate])
                .price(resultRaw[price])
                .unitOfMeasure(UnitOfMeasure.valueOf(resultRaw[unit_of_measure]))
                .manufacturer(Organization.Builder()
                    .name(resultRaw[manufacturer_name])
                    .fullName(resultRaw[full_name])
                    .type(OrganizationType.valueOf(resultRaw[organization_type]))
                    .build()!!)
                .owner(resultRaw[ownerName])
                .build()!!
        } catch (_: Exception) {
            return null
        }

    }

}

object productsFromDataBase: Table("products_full") {
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

object usersFromDataBase: Table("users") {
    val name = text("name")
    val pass = text("pass")
}