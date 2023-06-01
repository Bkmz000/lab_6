package client.command.processing



import AllExecuteSamples
import client.product.ProductBuilderCLI
import execute.packets.ExecutePacket
import execute.sample.ExecuteSample
import execute.sample.ExecuteType.*
import org.koin.core.component.KoinComponent
import java.io.File

object ExecutePacketBuilder : KoinComponent {


    fun getByMessage(message: String) : ExecutePacket?{

        val listOfWords = message.toListWithoutBlanks() ?: return null
        val commandName = listOfWords.removeFirst()
        val commandSample = AllExecuteSamples.samples
                                            .filter { it.equals(commandName) }
                                            .let { if(it.isNotEmpty()) it[0] else return null }

        val executeCommandPacket = when(commandSample.type) {
            ARGUMENT -> getArgumentExecutePacket(commandSample, listOfWords)
            OBJECT -> getObjectExecutePacket(commandSample, listOfWords)
            NON_ARGUMENT -> getNonArgumentExecutePacket(commandSample, listOfWords)
            SCRIPT -> getScriptExecutePacket(commandSample, listOfWords)
        }

        return executeCommandPacket



    }

    private fun String.toListWithoutBlanks() : MutableList<String>? {

        this.ifEmpty { null }
        val listOfWords = this.split(" ").toMutableList()
        listOfWords.removeAll { this.isBlank() }
        return listOfWords.ifEmpty { null }

    }


    private fun getNonArgumentExecutePacket(executeSample: ExecuteSample, listOfWords: MutableList<String>) : ExecutePacket?{
        return if(listOfWords.isEmpty()) ExecutePacket(executeSample.name) else null
    }

    private fun getArgumentExecutePacket(executeSample: ExecuteSample, possibleArgs: MutableList<String>) : ExecutePacket? {
        val commandArgs = possibleArgs.tryCastListAs(executeSample.typeOfArgs!!) ?: return null
        commandArgs as MutableList<Int>
        return ExecutePacket(executeSample.name, listOfIntArgs = commandArgs )

    }

    private fun getObjectExecutePacket(executeSample: ExecuteSample, possibleArgs: MutableList<String>) : ExecutePacket? {
        val commandArg = possibleArgs.tryCastListAs(executeSample.typeOfArgs!!) ?: return null
        commandArg as MutableList<Int>

        val product = ProductBuilderCLI().build() ?: return null

        return ExecutePacket(executeSample.name, listOfIntArgs =  commandArg, product =  product)
    }

    private fun getScriptExecutePacket(executeSample: ExecuteSample, possibleFileName: MutableList<String>) : ExecutePacket? {
        val fileName = possibleFileName[0]
        val fileWithScript = File(fileName).also { if (!it.isFile) return null }
        val stringsFromFile = fileWithScript.bufferedReader().readLines().toMutableList()

        return ExecutePacket(executeSample.name, listOfStringArgs =  stringsFromFile)
    }

    private fun parseStringsToScriptExecutePacket(stringsFromFile: MutableList<String>) {

    }





    private fun MutableList<String>.tryCastListAs(typeOfArgs: List<String>) : MutableList<Any>?{
        val possibleArgs = this
        if(typeOfArgs.size != possibleArgs.size) return null

        val commandArgs = mutableListOf<Any>()
        typeOfArgs.forEachIndexed { index, typeOfArg ->
            val argument = possibleArgs[index].tryCastStringAs(typeOfArg) ?: return null
            commandArgs.add(argument)
        }

        return commandArgs
    }


    private fun  String.tryCastStringAs(type: String) : Any? {
        return when(type) {
            "Int" -> this.toIntOrNull()
            "String" -> this
            else -> return null
        }
    }

}