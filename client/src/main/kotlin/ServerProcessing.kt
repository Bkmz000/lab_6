import client.command.processing.ExecutePacketBuilder
import execute.packets.ExecutePacket
import request.RequestPacket
import request.RequestType.COMMAND_EXECUTE
import request.RequestType.REFRESH_SAMPLES_INFORMATION
import java.util.Deque
import java.util.LinkedList

object ServerProcessing {

    private val needToSentMessages: Deque<RequestPacket> = LinkedList()
    private val expectedMessages = mutableSetOf<RequestPacket>()

    private val connectionRequestPacket = RequestPacket(REFRESH_SAMPLES_INFORMATION)


    fun process(){

        needToSentMessages.addFirst(connectionRequestPacket)
//        expectedMessages.add(connectionRequestPacket)

        while (true) {


            receiveUntilAnswer@
            while (needToSentMessages.isNotEmpty()) {




                    needToSentMessages.peekFirst()?. let {
                        Server.send(it)
                    }


                    var requestPacketFromServer: RequestPacket? = null

                    for (timeCount in 1..5) {
                        requestPacketFromServer = Server.receive()
                        if (requestPacketFromServer != null)
                            break
                        else {
                            print("...Waiting for server respond {$timeCount}\r")
                            Thread.sleep(300)
                        }
                    }

                    if(requestPacketFromServer == null) {
                        if (!needToSentMessages.contains(connectionRequestPacket))
                            needToSentMessages.addFirst(connectionRequestPacket)
                    } else {
                        if(requestPacketFromServer.requestType == COMMAND_EXECUTE) {
                            CommandHandler.processAndPrintResult(requestPacketFromServer)
                            needToSentMessages.clear()
                            break@receiveUntilAnswer

                        } else {
                            needToSentMessages.pollFirst()
                            CommandHandler.processAndPrintResult(requestPacketFromServer)
                        }

                    }

//
//                    if (requestPacketFromServer == null) {
//                        if (!needToSentMessages.contains(connectionRequestPacket)) needToSentMessages.addFirst(connectionRequestPacket)
//                        //expectedMessages.add(connectionRequestPacket)
//                        println("...Waiting for server respond")
//                        Thread.sleep(500)
//                    } else {
//
//                    }
//
//                    requestPacketFromServer = Server.receive()
//
//                    if (requestPacketFromServer != null && requestPacketFromServer.requestType == COMMAND_EXECUTE) {
//                        CommandHandler.processAndPrintResult(requestPacketFromServer)
//                        needToSentMessages.clear()
//                        break@receiveUntilAnswer
//                    }



            }

            val requestPacketFromClient = getRequestPacketFromClientByCLI()
            needToSentMessages.addFirst(requestPacketFromClient)
            //expectedMessages.add(requestPacketFromClient)





        }

    }



    private fun getRequestPacketFromClientByCLI(): RequestPacket {
        var executePacketFromClient: ExecutePacket?
        do {
            println("--Write down the command:")
            val messageFromClient = readln()
            executePacketFromClient = ExecutePacketBuilder.getByMessage(messageFromClient)
            if (executePacketFromClient == null) println("--Unknown command")
        } while (executePacketFromClient == null)
        return RequestPacket(COMMAND_EXECUTE, executePacket = executePacketFromClient)
    }




}