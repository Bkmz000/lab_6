package server.server

import com.google.gson.GsonBuilder
import server.command.interpritation.CommandManager
import java.net.InetSocketAddress
import java.nio.ByteBuffer
import java.nio.channels.DatagramChannel

class Server(private val address : InetSocketAddress ) {

    private val gson = GsonBuilder().create()


    fun start(){

        val channel = DatagramChannel.open()
                            channel.bind(address)
                            channel.configureBlocking(false)

        val buffer = ByteBuffer.allocateDirect(10000)
        println("Server started...")


        while (true){

            val clientAddress = channel.receive(buffer)
            val messageFromClient = extractMessage(buffer)
            if(clientAddress != null){
                buffer.clear()
                val resultOfRequest = CommandManager.interpretationRequestAndGetResult(messageFromClient)
                val resultAsJson = gson.toJson(resultOfRequest)
                println(resultAsJson)

                val resultAsBuffer = ByteBuffer.wrap(resultAsJson.toByteArray())

                channel.send(resultAsBuffer, clientAddress)
            } else {
                buffer.clear()
            }


        }
    }

    private fun extractMessage(buffer: ByteBuffer): String {
        buffer.flip()
        val bytes = ByteArray(buffer.remaining())
        buffer[bytes]
        return String(bytes)
    }


}


