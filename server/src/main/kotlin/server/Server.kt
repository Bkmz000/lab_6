package server.server

import ServerAddress.serverAddress
import com.google.gson.GsonBuilder
import request.RequestPacket
import server.command.interpritation.RequestFromClientHandler
import java.net.SocketAddress
import java.nio.ByteBuffer
import java.nio.channels.DatagramChannel

object Server {

    private val address = serverAddress
    private val gson = GsonBuilder().create()


    private val  requestQueue = ArrayDeque<Pair<SocketAddress, RequestPacket>>()



    @Synchronized fun addInQueue(pair: Pair<SocketAddress, RequestPacket>) {
        requestQueue.addLast(pair)
    }

    @Synchronized fun takeFromQueue(): Pair<SocketAddress, RequestPacket>? {
        return requestQueue.removeFirstOrNull()
    }



    fun start(){

        val channel = DatagramChannel.open()
                            channel.bind(address)
                            channel.configureBlocking(false)

        val buffer = ByteBuffer.allocate(10000)
        println("Server started...")


        val receiveMessageAndAddToQueue = Runnable {
            while (true) {
                buffer.clear()
                val clientAddress = channel.receive(buffer)
                val messageFromClient = extractMessage(buffer)
                if (clientAddress != null) {
                    buffer.clear()
                    println(messageFromClient)
                    val resultOfRequest = RequestFromClientHandler.interpretationRequestAndGetResult(messageFromClient)
                    addInQueue(Pair(clientAddress, resultOfRequest))
                }
            }
        }

        val sendMessageToClient = Runnable {
            loop@
            while (true){
                val requestPacket = takeFromQueue() ?: continue@loop
                val resultAsJson = gson.toJson(requestPacket.second)

                val resultAsBuffer = ByteBuffer.wrap(resultAsJson.toByteArray())

                channel.send(resultAsBuffer, requestPacket.first)
            }

        }



            val a = Thread(receiveMessageAndAddToQueue).start()
            val b = Thread(sendMessageToClient).start()



//        while (true){
//            buffer.clear()
//            val clientAddress = channel.receive(buffer)
//            val messageFromClient = extractMessage(buffer)
//
//
//            if(clientAddress != null){
//                buffer.clear()
//                println(messageFromClient)
//                val resultOfRequest = RequestFromClientHandler.interpretationRequestAndGetResult(messageFromClient)
//                val resultAsJson = gson.toJson(resultOfRequest)
//
//                val resultAsBuffer = ByteBuffer.wrap(resultAsJson.toByteArray())
//
//                channel.send(resultAsBuffer, clientAddress)
//            } else {
//                buffer.clear()
//            }
//
//
//        }
    }

    private fun extractMessage(buffer: ByteBuffer): String {
        buffer.flip()
        val bytes = ByteArray(buffer.remaining())
        buffer[bytes]
        return String(bytes)
    }


}


