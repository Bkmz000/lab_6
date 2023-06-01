

import client.command.processing.ExecutePacketBuilder
import com.google.gson.GsonBuilder
import request.RequestPacket
import request.RequestType.COMMAND_EXECUTE
import java.net.InetSocketAddress
import java.nio.ByteBuffer
import java.nio.channels.DatagramChannel

class notTheRealServer(private val address: InetSocketAddress) {

    private val gson = GsonBuilder().create()


    fun start(){

        val channel = DatagramChannel.open()
        val buffer = ByteBuffer.allocate(10000)
        println("The application is running...")

        while (true){
            val message = readln()
            val executePacket = ExecutePacketBuilder.getExecutePacket(message)
            if(executePacket == null) {
                println("Unknown command")
                continue
            }
            val requestPacket = RequestPacket(COMMAND_EXECUTE, executePacket = executePacket)
            val requestAsJson = gson.toJson(requestPacket)
            val requestAsBuffer = ByteBuffer.wrap(requestAsJson.toByteArray()) //!
            channel.send(requestAsBuffer, address)
            buffer.clear()
            channel.receive(buffer)
            val result = extractMessage(buffer)

                val resultAs = gson.fromJson(result, RequestPacket::class.java)
                println(resultAs.message)
                buffer.clear()


            buffer.clear()
        }
    }

    private fun extractMessage(buffer: ByteBuffer): String {
        buffer.flip()
        val bytes = ByteArray(buffer.remaining())
        buffer[bytes]
        return String(bytes)
    }

}