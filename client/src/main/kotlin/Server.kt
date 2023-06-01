import client.command.processing.ExecutePacketBuilder
import com.google.gson.GsonBuilder
import execute.packets.ExecutePacket
import request.RequestPacket
import request.RequestType
import request.RequestType.COMMAND_EXECUTE
import request.RequestType.CONNECTION_WITH_COMMANDS
import java.net.InetSocketAddress
import java.nio.ByteBuffer
import java.nio.channels.DatagramChannel
import java.util.*

class Server (val inetSocket: InetSocketAddress) {
     private val channel = DatagramChannel.open()
     private val messageStack = Stack<RequestPacket>()
     private val buffer = ByteBuffer.allocateDirect(8000)

     private val connectionRequest = RequestPacket(CONNECTION_WITH_COMMANDS)

     private val gson = GsonBuilder().create()



     fun getResult(){

          while (true){

               val message = readln()
               val executePacket = ExecutePacketBuilder.getExecutePacket(message)
               if(executePacket == null) {
                    println("Unknown command")
                    continue
               }
               val requestPacket = RequestPacket(COMMAND_EXECUTE, executePacket = executePacket)


               messageStack.push(requestPacket)
               var inc = true
               while (inc) {


                    send()
                    val result = receive()
                    if (result != null) {
                         println(result)
                         inc = false
                    }
               }


          }

     }




     fun send(){
          channel.configureBlocking(true)
          val requestPacket = messageStack.pop()
          buffer.clear()
          val requestAsJson = gson.toJson(requestPacket)
          val requestAsBuffer = ByteBuffer.wrap(requestAsJson.toByteArray())
          channel.send(requestAsBuffer, inetSocket)

     }



     fun receive() : RequestPacket?{
          channel.configureBlocking(false)
          buffer.clear()
          channel.receive(buffer)
          val messageFromTheServer = extractMessage(buffer)
          buffer.clear()

          if(messageFromTheServer.isBlank()) {
               messageStack.push(connectionRequest)
               println("...Waiting message from the server")
               Thread.sleep(1000)
               return null
          } else {
               val messageAsJson = gson.fromJson(messageFromTheServer, RequestPacket::class.java)
               return messageAsJson
          }


     }

     private fun extractMessage(buffer: ByteBuffer): String {
          buffer.flip()
          val bytes = ByteArray(buffer.remaining())
          buffer[bytes]
          return String(bytes)
     }
}