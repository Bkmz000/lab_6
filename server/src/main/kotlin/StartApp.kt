package server


import org.koin.core.component.KoinComponent
import server.command.`object`.file.LoadCollection
import server.server.Server


class StartApp : KoinComponent {



    fun start() {

        println(LoadCollection.load())

        Server.start()


    }
}



