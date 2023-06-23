package execute.packets


import product.Product


open class ExecutePacket(
    val commandName: String,
    val listOfIntArgs: MutableList<Int>? = null,
    val listOfStringArgs: MutableList<String>? = null,
    val product: Product? = null,
    val listOfExecutePacketsForScript: MutableList<ExecutePacket>? = null,
    val token:String? = null,
    ) {

    override fun toString(): String {
        return "ExecutePacket(commandName='$commandName', listOfIntArgs=$listOfIntArgs, listOfStringArgs=$listOfStringArgs, product=$product, listOfExecutePacketsForScript=$listOfExecutePacketsForScript)"
    }
}