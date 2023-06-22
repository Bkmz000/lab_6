package product

import kotlinx.serialization.Serializable
import java.time.LocalDateTime
@Suppress("PROVIDED_RUNTIME_TOO_LOW")
@Serializable
class Organization private constructor(
    val name: String,
    val fullName: String,
    val type: OrganizationType,
    ){

    data class Builder (
        private var name: String? = null,
        private var fullName: String? = null,
        private var type: OrganizationType? = null,
    ){

        fun name(name: String) = apply { this.name = name }
        fun fullName(fullName: String) = apply { this.fullName = fullName }
        fun type(type: OrganizationType) = apply { this.type = type }

        private fun isBuildEnough() = !(name.isNullOrEmpty() || fullName.isNullOrEmpty() || type == null )


        fun build(): Organization? {
            return if (isBuildEnough()){
                Organization(name!!,fullName!!,type!!)
            } else
                null
        }
    }






    override fun toString(): String {
        return "Organization(name='$name', fullName='$fullName', type=$type)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Organization

        if (name != other.name) return false
        if (fullName != other.fullName) return false
        return type == other.type
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + fullName.hashCode()
        result = 31 * result + type.hashCode()
        return result
    }


}