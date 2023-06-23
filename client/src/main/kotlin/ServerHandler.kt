import client.command.processing.ExecutePacketBuilder
import execute.packets.ExecutePacket
import request.RequestPacket
import request.RequestType.*
import java.util.*

object ServerHandler {

    private const val timeOutNumber = 20
    private var timeOutCounter = 0
    var isRegistered = false

    private val needToSentMessages: Deque<RequestPacket> = LinkedList()
    private val connectionRequestPacket = RequestPacket(REFRESH_SAMPLES_INFORMATION)


    fun process(): Boolean{

        needToSentMessages.addFirst(connectionRequestPacket)
        timeOutCounter = 0

        mainLoop@
        while (true) {

            receiveUntilAnswer@
            while (needToSentMessages.isNotEmpty()) {




                    needToSentMessages.peekFirst()?. let {
                        ChannelProcessor.send(it)
                    }


                    var requestPacketFromServer: RequestPacket? = null

                    for (timeCount in 1..5) {
                        requestPacketFromServer = ChannelProcessor.receive()
                        if (requestPacketFromServer != null)
                            break
                        else {
                            print("...Waiting for server respond {$timeCount}\r")
                            Thread.sleep(300)
                        }
                    }

                    if(requestPacketFromServer == null) {
                        if(timeOutCounter >= timeOutNumber) return false else timeOutCounter++

                        if (!needToSentMessages.contains(connectionRequestPacket))
                            needToSentMessages.addFirst(connectionRequestPacket)
                    } else {
                        if(requestPacketFromServer.requestType == COMMAND_EXECUTE) {
                            ServerRequestProcessor.processAndPrintResult(requestPacketFromServer)
                            needToSentMessages.clear()
                            break@receiveUntilAnswer

                        } else {
                            needToSentMessages.pollFirst()
                            ServerRequestProcessor.processAndPrintResult(requestPacketFromServer)
                        }
                    }
            }

            val requestPacketFromClient = getRequestPacketFromClientByCLI()
            needToSentMessages.addFirst(requestPacketFromClient)
        }
    }



    private fun getRequestPacketFromClientByCLI(): RequestPacket {

        if (!isRegistered) {
            println("Write down the login:")
            val login = readln()
            println("Write down the password:")
            val pass = readln()
            return RequestPacket(LOGIN, login = login, pass = pass)
        } else {
            var executePacketFromClient: Pair<ExecutePacket?, String?>
            do {
                println("--Write down the command:")
                val messageFromClient = readln()
                executePacketFromClient = ExecutePacketBuilder().getByMessage(messageFromClient)
                if (executePacketFromClient.first == null)
                    println(executePacketFromClient.second)
            } while (executePacketFromClient.first == null)
            return RequestPacket(COMMAND_EXECUTE, executePacket = executePacketFromClient.first)
        }
    }




}