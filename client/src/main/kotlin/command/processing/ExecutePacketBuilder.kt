package client.command.processing



import AllExecuteSamples
import client.product.ProductBuilderCLI
import execute.packets.ExecutePacket
import execute.sample.ExecuteSample
import execute.sample.ExecuteType.*
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.io.File

object ExecutePacketBuilder : KoinComponent {

    private val allCommandSamples by inject<AllExecuteSamples>()

    fun getExecutePacket(message: String) : ExecutePacket?{

        val listOfWords = message.toListWithoutBlanks() ?: return null
        val commandName = listOfWords.removeFirst()
        val commandSample = allCommandSamples.samples
                                            .filter { it.equals(commandName) }
                                            .let { if(it.isNotEmpty()) it[0] else return null }

        val executeCommandPacket = when(commandSample.type) {
            ARGUMENT -> getArgumentCommandPacket(commandSample, listOfWords)
            OBJECT -> getObjectCommandPacket(commandSample, listOfWords)
            NON_ARGUMENT -> getNonArgumentCommandPacket(commandSample, listOfWords)
            SCRIPT -> getScriptCommandPacket(commandSample, listOfWords)
        }

        return executeCommandPacket



    }

    private fun String.toListWithoutBlanks() : MutableList<String>? {

        this.ifEmpty { null }
        val listOfWords = this.split(" ").toMutableList()
        listOfWords.removeAll { this.isBlank() }
        return listOfWords.ifEmpty { null }

    }


    private fun getNonArgumentCommandPacket(executeSample: ExecuteSample, listOfWords: MutableList<String>) : ExecutePacket?{
        return if(listOfWords.isEmpty()) ExecutePacket(executeSample.name) else null
    }

    private fun getArgumentCommandPacket(executeSample: ExecuteSample, possibleArgs: MutableList<String>) : ExecutePacket? {
        val commandArgs = possibleArgs.tryCastListAs(executeSample.typeOfArgs!!) ?: return null
        commandArgs as MutableList<Int>
        return ExecutePacket(executeSample.name, listOfNumberArgs = commandArgs )

    }

    private fun getObjectCommandPacket(executeSample: ExecuteSample, possibleArgs: MutableList<String>) : ExecutePacket? {
        val commandArg = possibleArgs.tryCastListAs(executeSample.typeOfArgs!!) ?: return null
        commandArg as MutableList<Int>

        val product = ProductBuilderCLI().build() ?: return null

        return ExecutePacket(executeSample.name, listOfNumberArgs =  commandArg, product =  product)
    }

    private fun getScriptCommandPacket(executeSample: ExecuteSample, possibleFileName: MutableList<String>) : ExecutePacket? {
        val fileName = possibleFileName[0]
        val fileWithScript = File(fileName).also { if (!it.isFile) return null }
        val linesFromFile = fileWithScript.bufferedReader().readLines().toMutableList()

        return ExecutePacket(executeSample.name, listOfStringArgs =  linesFromFile)
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
            "Double" -> this.toDoubleOrNull()
            "String" -> this
            else -> return null
        }
    }

}