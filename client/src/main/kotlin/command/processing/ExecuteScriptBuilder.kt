package command.processing

import client.command.processing.ExecutePacketBuilder
import execute.packets.ExecutePacket
import execute.sample.ExecuteSample

object ExecuteScriptBuilder {

    private lateinit var stringsFromFile: MutableList<String>
    private lateinit var executeSample: ExecuteSample
    fun build(stringsFromFile: MutableList<String>, executeSample: ExecuteSample) : Pair<ExecutePacket?, String?>{
        this.stringsFromFile = stringsFromFile
        this.executeSample = executeSample

        val recursiveExecutePacketBuilder = ExecutePacketBuilder().also { it.launchedByScript = true }

        val executePacketsFromFile = mutableListOf<ExecutePacket>()

        while (stringsFromFile.isNotEmpty()) {
            val currentString = stringsFromFile.removeFirstOrNull() ?: return Pair(null, "--The file is empty")
            val currentExecutePacket = recursiveExecutePacketBuilder.getByMessage(currentString)

            if(currentExecutePacket.first == null)
                return currentExecutePacket
            else
                executePacketsFromFile.add(currentExecutePacket.first!!)
        }
        return Pair(ExecutePacket(executeSample.name, listOfExecutePacketsForScript = executePacketsFromFile, token = Token.token), null)
    }

    fun getStringsFromFileForBuildingProduct(): MutableList<String>?{
        val argsForProduct = mutableListOf<String>()
        for (index in 0..7) {
            argsForProduct.add(
                stringsFromFile.removeFirstOrNull()
                ?: return null)
        }
        return argsForProduct
    }


}