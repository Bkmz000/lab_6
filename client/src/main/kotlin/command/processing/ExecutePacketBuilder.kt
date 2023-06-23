package client.command.processing



import AllExecuteSamples
import Token
import client.product.ProductBuilderCLI
import command.processing.ExecuteScriptBuilder
import command.processing.ExecuteScriptBuilder.getStringsFromFileForBuildingProduct
import execute.packets.ExecutePacket
import execute.sample.ExecuteSample
import execute.sample.ExecuteType.*
import org.koin.core.component.KoinComponent
import org.koin.core.logger.MESSAGE
import product.Product
import product.ProductBuilderScript
import java.io.File

class ExecutePacketBuilder : KoinComponent {

    var launchedByScript = false

    fun getByMessage(message: String) : Pair<ExecutePacket?, String?>{

        val listOfWords = message.toListWithoutBlanks() ?:
            return Pair(null, "--The message is empty")
        val commandName = listOfWords.removeFirst()
        val commandSample = AllExecuteSamples.samples
                                            .filter { it.equals(commandName) }
                                            .let {
                                                if(it.isNotEmpty())
                                                    it[0]
                                                else
                                                    return Pair(null, "--No such command - $commandName") }

        val pairOfExecuteCommandPacketAndResultMessage = when(commandSample.type) {
            ARGUMENT -> getArgumentExecutePacket(commandSample, listOfWords)
            OBJECT -> getObjectExecutePacket(commandSample, listOfWords)
            NON_ARGUMENT -> getNonArgumentExecutePacket(commandSample, listOfWords)
            SCRIPT -> getScriptExecutePacket(commandSample, listOfWords)
        }

        return pairOfExecuteCommandPacketAndResultMessage



    }


    private fun String.toListWithoutBlanks() : MutableList<String>? {

        this.ifEmpty { null }
        val listOfWords = this.split(" ").toMutableList()
        listOfWords.removeAll { this.isBlank() }
        return listOfWords.ifEmpty { null }

    }


    private fun getNonArgumentExecutePacket(executeSample: ExecuteSample, listOfWords: MutableList<String>) : Pair<ExecutePacket?, String?> {

            return if(listOfWords.isEmpty())
                Pair(ExecutePacket(executeSample.name, token = Token.token), null)
            else
                Pair(null, "--This command (${executeSample.name}) requires no arguments")

    }

    private fun getArgumentExecutePacket(executeSample: ExecuteSample, possibleArgs: MutableList<String>) : Pair<ExecutePacket?, String?> {

        val commandArgs = possibleArgs.tryCastListAs(executeSample.typeOfArgs!!)
            ?: return Pair(null, "--Invalid arguments of the (${executeSample.name}) command")

        commandArgs as MutableList<Int>
        return Pair(ExecutePacket(executeSample.name, listOfIntArgs = commandArgs, token = Token.token), null,)
    }

    private fun getObjectExecutePacket(executeSample: ExecuteSample, possibleArgs: MutableList<String>) : Pair<ExecutePacket?, String?> {

        val commandArg = possibleArgs.tryCastListAs(executeSample.typeOfArgs!!)
            ?: return Pair(null, "--Invalid arguments of the (${executeSample.name}) command")
        commandArg as MutableList<Int>

        val product: Product = if(launchedByScript) {
            val argsForProduct = getStringsFromFileForBuildingProduct()
                ?: return Pair(null, "--The product by command (${executeSample.name}) was not created (not enough arguments)")
            ProductBuilderScript().build(argsForProduct)
                ?: return Pair(null, "--The product by command (${executeSample.name}) was not created (wrong arguments)")
        } else {
            ProductBuilderCLI().build()
                ?: return Pair(null, "--The product by command (${executeSample.name}) was not created")
        }

        return Pair(ExecutePacket(executeSample.name, listOfIntArgs =  commandArg, product =  product, token = Token.token),null)

    }

    private fun getScriptExecutePacket(executeSample: ExecuteSample, possibleFileName: MutableList<String>) : Pair<ExecutePacket?, String?> {

        if (launchedByScript) return Pair(null, "--Scripts cannot be called recursively")

        val fileName = possibleFileName[0]
        val fileWithScript = File(fileName).also { if (!it.isFile) return Pair(null, "The file was not found") }
        val textFromFile = fileWithScript.bufferedReader().readLines().toMutableList()

        val pairOfExecutePacketAndResultMessage = ExecuteScriptBuilder.build(textFromFile, executeSample)
        if (pairOfExecutePacketAndResultMessage.first == null) return pairOfExecutePacketAndResultMessage

        val executePacket = pairOfExecutePacketAndResultMessage.first

        return Pair(executePacket ,null)
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

//    fun f1(crossinline block: String.() -> Unit){
//        block()
//    }
//теория категорий
}