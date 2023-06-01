import com.google.gson.GsonBuilder
import com.sun.org.apache.xerces.internal.util.SecurityManager
import request.RequestPacket
import java.net.InetSocketAddress
import java.net.PortUnreachableException
import java.net.SocketException
import java.nio.ByteBuffer
import java.nio.channels.AlreadyConnectedException
import java.nio.channels.Channel
import java.nio.channels.DatagramChannel
import java.util.concurrent.Future

object Server {
    var inetSocket = InetSocketAddress("localhost", 6653)
    private val channel = DatagramChannel.open()
    private val buffer = ByteBuffer.allocateDirect(8000)

    private val gson = GsonBuilder().create()





     fun send(requestPacket: RequestPacket){
          channel.configureBlocking(true)

          val requestAsJson = gson.toJson(requestPacket)
          val requestAsBuffer = ByteBuffer.wrap(requestAsJson.toByteArray())
          channel.send(requestAsBuffer, inetSocket)


     }



     fun receive() : RequestPacket?{
          channel.configureBlocking(false)
         Thread.sleep(100)

          buffer.clear()
          channel.receive(buffer)

          val messageFromTheServer = extractMessage(buffer)
          return messageFromTheServer.fromJsonOrNull()


     }

     private fun extractMessage(buffer: ByteBuffer): String {
          buffer.flip()
          val bytes = ByteArray(buffer.remaining())
          buffer[bytes]
          return String(bytes)
     }

     private fun String.fromJsonOrNull() : RequestPacket? {
          return try {
               gson.fromJson(this, RequestPacket::class.java)
          } catch (e: Exception) {
               null
          }
     }
}