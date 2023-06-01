package execute.packets


import product.Product


class ExecutePacket(
    val commandName: String,
    val listOfIntArgs: MutableList<Int>? = null,
    val listOfStringArgs: MutableList<String>? = null,
    val product: Product? = null,
    )