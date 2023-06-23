package server


import PasswordUtils
import collection.Database
import collection.Users
import org.koin.core.component.KoinComponent
import server.server.Server
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import java.util.*


class StartApp : KoinComponent {



    fun start() {

        println(Database.loadCollections())
        println(Users.loginPass)
        Server.start()


    }

}



