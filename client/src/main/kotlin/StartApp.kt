import execute.packets.ExecutePacket

object StartApp {

    lateinit var a: ExecutePacket
    fun start(){


        ServerHandler.process()
        println("--Connection with the server lost. Try to restart app later")

    }

}