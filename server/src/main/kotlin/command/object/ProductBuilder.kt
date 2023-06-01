package server.command.`object`

import product.Product

abstract class ProductBuilder {
    abstract fun build(args: Any? = null): Product?

}