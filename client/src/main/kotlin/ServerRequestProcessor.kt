import execute.sample.ExecuteSample
import request.RequestPacket
import request.RequestType.*

object ServerRequestProcessor {


    fun processAndPrintResult(requestPacket: RequestPacket) {

        when (requestPacket.requestType) {
            COMMAND_EXECUTE -> {
                println("--Server responded:")
                println(requestPacket.message)
            }
            REFRESH_SAMPLES_INFORMATION -> {
                refreshSamplesInformation(requestPacket.executeSamples!!)
            }
            LOGIN -> {
                if(requestPacket.message == null) {
                    println("Invalid username or password")
                } else {
                    Token.token = requestPacket.message!!
                    Token.rightLogin = requestPacket.login!!
                    ServerHandler.isRegistered = true
                }
            }
        }

    }

    private fun refreshSamplesInformation(listOfServerSamples : List<ExecuteSample>) {

        val missingSamples = listOfServerSamples.minus(AllExecuteSamples.samples)
        if(missingSamples.isNotEmpty()) {
            missingSamples.forEach {
                AllExecuteSamples.addSample(it)
            }
        }

    }

}