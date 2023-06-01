package client

import java.net.InetSocketAddress

class StartApp {


    fun start(){



        val inetSocketAddress = InetSocketAddress("localhost", 6653)
        val serv = Server(inetSocketAddress)
        serv.start()





//        println("Welcome to the CLI \"Product Collection\"")
//        while(true) {
//            val messageFromUser = readln()
//            val commandPacket = ExecutePacketBuilder.getExecutePacket(messageFromUser)
//
//
//            if(commandPacket != null) {
//                val requestPacket = RequestPacket(RequestType.COMMAND_EXECUTE, commandPacket)
//                println(requestPacket)
//            } else {
//
//                println("Unknown command")
//            }
//        }
    }

}