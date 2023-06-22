package server


import collection.Database
import org.koin.core.component.KoinComponent
import server.server.Server


class StartApp : KoinComponent {



    fun start() {

        println(Database.loadProductCollectionToApp())

        Server.start()


    }
}



