package product
import kotlinx.serialization.Serializable
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Suppress("PROVIDED_RUNTIME_TOO_LOW")
@Serializable
class Product private constructor(
    val name: String,
    val coordinates: Coordinates,
    val creationDate: String,
    val price: Int,
    val unitOfMeasure: UnitOfMeasure,
    val manufacturer: Organization,
    val owner: String
){
    data class Builder(
        private var name: String? = null,
        private var coordinates: Coordinates? = null,
        private var creationDate: String? = null,
        private var price: Int? = null,
        private var unitOfMeasure: UnitOfMeasure? = null,
        private var manufacturer: Organization? = null,
        private var owner: String? = null
    ) {
        fun name(name: String) = apply { this.name = name }
        fun coordinates(coordinates: Coordinates) = apply { this.coordinates = coordinates }
        fun creationDate(date: String) = apply { this.creationDate = date }
        fun price(price: Int) = apply { this.price = price }
        fun unitOfMeasure(unitOfMeasure: UnitOfMeasure) = apply { this.unitOfMeasure = unitOfMeasure }
        fun manufacturer(manufacturer: Organization) = apply { this.manufacturer = manufacturer }
        fun owner(owner: String) = apply { this.owner = owner }

        private fun isBuildEnough() = !(name.isNullOrEmpty() || coordinates == null || price == null
                || unitOfMeasure == null || manufacturer == null || owner == null)


        fun build(): Product? {
            return if (isBuildEnough()) {
                if (creationDate == null) {
                    creationDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
                }
                Product(name!!,coordinates!!,creationDate!!,price!!,unitOfMeasure!!,manufacturer!!, owner!!)
            } else
                null
        }
    }





    override fun toString(): String {
        return """
            |Product( 
            |coordinates=$coordinates, 
            |creationDate='$creationDate', price=$price, 
            |unitOfMeasure=$unitOfMeasure, 
            |manufacturer=$manufacturer)
            |owner_name=$owner""".trimMargin()
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + coordinates.hashCode()
        result = 31 * result + creationDate.hashCode()
        result = 31 * result + price
        result = 31 * result + unitOfMeasure.hashCode()
        result = 31 * result + manufacturer.hashCode()
        result = 31 * result + owner.hashCode()
        return result
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Product

        if (name != other.name) return false
        if (coordinates != other.coordinates) return false
        if (creationDate != other.creationDate) return false
        if (price != other.price) return false
        if (unitOfMeasure != other.unitOfMeasure) return false
        if (manufacturer != other.manufacturer) return false
        return owner == other.owner
    }


}