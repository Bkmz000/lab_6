package product


class ProductBuilderScript {

    private val product = Product.Builder()
    private lateinit var listOfProductAttributes: MutableList<String>



    fun build(listOfProductAttributes: MutableList<String>): Product? {

        this.listOfProductAttributes = listOfProductAttributes
        setName()
        setCoordinate()
        setPrice()
        setUnitOfMeasure()
        setOrganization()
        return product.build()


    }

    private fun setName(){
        readLineUntilCondition(listOfProductAttributes[0]) { true }?.let { product.name(it) }
    }

    private fun setCoordinate(){
        val x = readLineUntilCondition(listOfProductAttributes[1]) { s: String -> s.toIntOrNull() != null}
        val y = readLineUntilCondition(listOfProductAttributes[2]) { s: String -> s.toDoubleOrNull() != null}
        if (x != null && y != null) {
            product.coordinates(Coordinates(x.toInt(),y.toDouble()))
        }
    }

    private fun setPrice(){
        val price = readLineUntilCondition(listOfProductAttributes[3]) { s: String -> s.toIntOrNull() != null }
        if (price != null) {
            product.price(price.toInt())
        }
    }
    private fun setUnitOfMeasure(){
        fun checkIfUnitOfMeasure(s:String): Boolean {
            try {
                UnitOfMeasure.valueOf(s)
            } catch (e: IllegalArgumentException){
                return false
            }
            return true
        }


        val unitOfMeasure = readLineUntilCondition(listOfProductAttributes[4]) { s:String -> checkIfUnitOfMeasure(s) }
        unitOfMeasure?.let { UnitOfMeasure.valueOf(it) }?.let { product.unitOfMeasure(it) }
    }

    private fun setOrganization() {
        fun checkIfOrganizationType(s:String):Boolean{
            try {
                OrganizationType.valueOf(s)
            } catch (e: IllegalArgumentException){
                return false
            }
            return true
        }



        val organization = Organization.Builder()

        readLineUntilCondition(listOfProductAttributes[5]) { true }?.let { organization.name(it) }
        readLineUntilCondition(listOfProductAttributes[6]) {true}?.let { organization.fullName(it) }

        val organizationType = readLineUntilCondition(listOfProductAttributes[7]) { s: String -> checkIfOrganizationType(s)}
        organizationType?.let { OrganizationType.valueOf(it) }?.let { organization.type(it) }

        organization.build()?.let { product.manufacturer(it) }
    }


    private fun readLineUntilCondition(messageFromUser: String, condition: (String) -> Boolean): String?{
            if(messageFromUser.isNotEmpty()){
                if(condition(messageFromUser)){
                    return messageFromUser
                }
            }
            return null
    }
}