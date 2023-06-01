import execute.sample.ExecuteSample
import request.RequestPacket
import request.RequestType.COMMAND_EXECUTE
import request.RequestType.REFRESH_SAMPLES_INFORMATION

object CommandHandler {


    fun processAndPrintResult(requestPacket: RequestPacket) {

        when (requestPacket.requestType) {
            COMMAND_EXECUTE -> {
                println("--Server responded:")
                println(requestPacket.message)
            }
            REFRESH_SAMPLES_INFORMATION -> {
                refreshSamplesInformation(requestPacket.executeSamples!!)
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