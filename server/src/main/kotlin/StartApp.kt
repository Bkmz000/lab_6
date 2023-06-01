package server


import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import server.command.invoke.CommandInvoker
import server.command.`object`.file.LoadCollection
import server.server.Server
import java.net.InetSocketAddress


class StartApp : KoinComponent {

    private val commandInvoker by inject<CommandInvoker>()


    fun start() {

        println(LoadCollection.load())

        val inetSocketAddress = InetSocketAddress("localhost", 6653)

        val server = Server(inetSocketAddress)
        server.start()

//        println(Json.decodeFromJsonElement(LoadCollection().load()) as String)
//
//            val a = ExecutePacket(ExecuteSample("show",ExecuteType.NON_ARGUMENT))
//            println(a)
//            val b = ExecuteCommandBuilder.getExecuteCommand(a)?.let {
//                commandInvoker.executeCommand(it)
//            } ?: println("Error")

    }
}



