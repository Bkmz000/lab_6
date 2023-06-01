import execute.sample.ExecuteSample
import execute.sample.ExecuteType.*


object AllExecuteSamples {
    val samples = mutableSetOf<ExecuteSample>()

    init{
        addSample(ExecuteSample("insert", OBJECT, listOf("Int")))
        addSample(ExecuteSample("remove", ARGUMENT, listOf("Int")))
        addSample(ExecuteSample("show", NON_ARGUMENT))
        addSample(ExecuteSample("info", NON_ARGUMENT))
        addSample(ExecuteSample("execute_script", SCRIPT, listOf("String")))
        addSample(ExecuteSample("help", NON_ARGUMENT))
        addSample(ExecuteSample("update", OBJECT, listOf("Int")))
        addSample(ExecuteSample("replace_if_greater", ARGUMENT, listOf("Int", "Int")))
        addSample(ExecuteSample("remove_greater_key", ARGUMENT, listOf("Int")))
        addSample(ExecuteSample("max_by_coordinates", NON_ARGUMENT))
        addSample(ExecuteSample("filter_starts_with_name", ARGUMENT, listOf("String")))
        addSample(ExecuteSample("filter_greater_than_manufacturer", ARGUMENT, listOf("String")))
        addSample(ExecuteSample("save", NON_ARGUMENT))
        addSample(ExecuteSample("execute_script", SCRIPT, listOf("String")))
        addSample(ExecuteSample("history", NON_ARGUMENT))
        addSample(ExecuteSample("info", NON_ARGUMENT))
        addSample(ExecuteSample("clear", NON_ARGUMENT))
        addSample(ExecuteSample("execute_script", SCRIPT, listOf("String")))
    }

    fun addSample(executeSample: ExecuteSample) {
        if(executeSample.type != NON_ARGUMENT && executeSample.typeOfArgs == null) return
        samples.add(executeSample)
    }
}