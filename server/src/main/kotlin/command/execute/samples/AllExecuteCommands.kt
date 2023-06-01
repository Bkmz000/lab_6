package server.command.execute.samples

import execute.sample.ExecuteSample
import execute.sample.ExecuteType.*
import server.command.execute.*
import kotlin.reflect.KClass

object AllExecuteCommands {

    val samples = mutableListOf<ExecuteSample>()
    val classesWithNames = HashMap<String, KClass<out ClientCommand>>(25, 0.75F)

    init {
        addExecuteCommand(ExecuteSample("insert", OBJECT,listOf("Int")), Insert::class)
        addExecuteCommand(ExecuteSample("help", NON_ARGUMENT), Help::class)
        addExecuteCommand(ExecuteSample("update", OBJECT, listOf("Int")), commandClass = Update::class)
        addExecuteCommand(ExecuteSample("remove", ARGUMENT, listOf("Int")), Remove::class)
        addExecuteCommand(ExecuteSample("replace_if_greater", ARGUMENT, listOf("Int", "Int")), ReplaceIfGreater::class)
        addExecuteCommand(ExecuteSample("remove_greater_key", ARGUMENT, listOf("Int")), RemoveGreaterKey::class)
        addExecuteCommand(ExecuteSample("show", NON_ARGUMENT), Show::class)
        addExecuteCommand(ExecuteSample("max_by_coordinates", NON_ARGUMENT), MaxByCoordinates::class)
        addExecuteCommand(ExecuteSample("filter_starts_with_name", ARGUMENT, listOf("String")), FilterStartsWithName::class)
        addExecuteCommand(ExecuteSample("filter_greater_than_manufacturer", ARGUMENT, listOf("String")), FilterGreaterThanManufacturer::class)
        addExecuteCommand(ExecuteSample("save", NON_ARGUMENT), Save::class)
        addExecuteCommand(ExecuteSample("execute_script", SCRIPT, listOf("String")), ExecuteScript::class)
        addExecuteCommand(ExecuteSample("history", NON_ARGUMENT), History::class)
        addExecuteCommand(ExecuteSample("info", NON_ARGUMENT), Info::class)
        addExecuteCommand(ExecuteSample("clear", NON_ARGUMENT), Clear::class)
        addExecuteCommand(ExecuteSample("execute_script", SCRIPT, listOf("String")), ExecuteScript::class)
    }

    private fun addExecuteCommand(executeSample: ExecuteSample, commandClass : KClass<out ClientCommand>){
        val commandName = executeSample.name

        addClass(commandName, commandClass)
        addSample(executeSample)

    }

    private fun addSample(executeSample: ExecuteSample) {
        if(executeSample.type != NON_ARGUMENT && executeSample.typeOfArgs == null) return
        samples.add(executeSample)
    }

    private fun addClass(commandName : String, commandClass: KClass<out ClientCommand>){
        classesWithNames[commandName] = commandClass
    }

}