package request

import execute.packets.ExecutePacket
import execute.sample.ExecuteSample


data class RequestPacket (
    val requestType: RequestType,
    var message: String? = null,
    val executePacket: ExecutePacket? = null,
    val executeSamples: List<ExecuteSample>? = null,
    ) {




    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as RequestPacket

        return requestType == other.requestType
    }

    override fun hashCode(): Int {
        return requestType.hashCode()
    }

    override fun toString(): String {
        return "RequestPacket(requestType=$requestType, message=$message, executePacket=$executePacket, executeSamples=$executeSamples)"
    }


}