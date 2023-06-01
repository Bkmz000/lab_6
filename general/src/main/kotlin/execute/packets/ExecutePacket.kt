package execute.packets


import product.Product


data class ExecutePacket(
    val commandName: String,
    val listOfNumberArgs: MutableList<Int>? = null,
    val listOfStringArgs: MutableList<String>? = null,
    val product: Product? = null,
    )