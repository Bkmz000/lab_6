package client.product

import Token
import product.*

class ProductBuilderCLI{

    private val product = Product.Builder()

    fun build(): Product? {
        println("Write down the fields values: ")
        setName()
        setCoordinate()
        setPrice()
        setUnitOfMeasure()
        setOrganization()
        setOwner()
        return product.build()

    }

    private fun setName(){
        product.name(readLineUntilCondition("name: ") { true })
    }

    private fun setCoordinate(){
        println("coordinate: ")
        val x = readLineUntilCondition("x: ") { s: String -> s.toIntOrNull() != null}
        val y = readLineUntilCondition("y: ") { s: String -> s.toDoubleOrNull() != null}
        product.coordinates(Coordinates(x.toInt(),y.toDouble()))
    }

    private fun setPrice(){
        val price = readLineUntilCondition("price: ") { s: String -> s.toIntOrNull() != null }
        product.price(price.toInt())
    }
    private fun setUnitOfMeasure(){
        fun checkIfUnitOfMeasure(s:String):Boolean{
            try {
                UnitOfMeasure.valueOf(s)
            } catch (e: IllegalArgumentException){
                return false
            }
            return true
        }


        val unitOfMeasure = readLineUntilCondition(
            "unit if measure (${enumValues<UnitOfMeasure>().asList()}): ") { s:String -> checkIfUnitOfMeasure(s) }
        product.unitOfMeasure(UnitOfMeasure.valueOf(unitOfMeasure))
    }

    private fun setOrganization(){
        fun checkIfOrganizationType(s:String):Boolean{
            try {
                OrganizationType.valueOf(s)
            } catch (e: IllegalArgumentException){
                return false
            }
            return true
        }



        val organization = Organization.Builder()
        println("organization: ")
        organization.name(readLineUntilCondition("name: ") { true })
                    .fullName(readLineUntilCondition("full name: ") {true})
        val organizationType = readLineUntilCondition(
            "organization type (${enumValues<OrganizationType>().asList()}): ") { s: String -> checkIfOrganizationType(s)}
        organization.type(OrganizationType.valueOf(organizationType))
        organization.build()?.let { product.manufacturer(it) }
    }

    private fun setOwner() {
        product.owner(Token.rightLogin)
    }


    private fun readLineUntilCondition(prefixString: String, condition: (String) -> Boolean): String{
        var messageFromUser:String
        while (true){
            print(prefixString)
            readln().also { messageFromUser = it }
            if(messageFromUser.isNotEmpty()){
                if(condition(messageFromUser)){
                    return messageFromUser
                }
            }
            println("Message is incorrect. Please try again!")
        }
    }
}