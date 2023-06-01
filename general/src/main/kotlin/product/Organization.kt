package product

import kotlinx.serialization.Serializable
import java.time.LocalDateTime
@Suppress("PROVIDED_RUNTIME_TOO_LOW")
@Serializable
class Organization private constructor(
    private val id: Int,
    val name: String,
    private val fullName: String,
    private val type: OrganizationType,
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
            if (isBuildEnough()){
                val id = LocalDateTime.now().nano - LocalDateTime.of(1900,1,1,1,1).nano
                return Organization(id,name!!,fullName!!,type!!)
            } else
                return null
        }
    }




    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Organization

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        val prime = 345353
        return id * prime
    }

    override fun toString(): String {
        return "Organization(id=$id, name='$name', fullName='$fullName', type=$type)"
    }


}