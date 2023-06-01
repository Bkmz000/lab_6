package request

import execute.packets.ExecutePacket
import execute.sample.ExecuteSample


data class RequestPacket (
    val requestType: RequestType,
    var message: String? = null,
    val executePacket: ExecutePacket? = null,
    val executeSamples: List<ExecuteSample>? = null,
    )