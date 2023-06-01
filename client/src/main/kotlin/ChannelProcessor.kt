import ServerAddress.serverAddress
import com.google.gson.GsonBuilder
import request.RequestPacket
import java.nio.ByteBuffer
import java.nio.channels.DatagramChannel

object ChannelProcessor {
    private val inetSocket = serverAddress
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