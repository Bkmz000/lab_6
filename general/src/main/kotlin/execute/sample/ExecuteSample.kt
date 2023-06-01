package execute.sample

import kotlinx.serialization.Serializable

@Suppress("PROVIDED_RUNTIME_TOO_LOW")
@Serializable
data class ExecuteSample(
    val name: String,
    val type: ExecuteType,
    val typeOfArgs: List<String>? = null){


    override fun equals(other: Any?): Boolean {
        if (this === other) return true

        if (name != other) return false

        return true
    }

    override fun hashCode(): Int {
        return name.hashCode()
    }
}