import org.koin.core.context.startKoin
import org.koin.java.KoinJavaComponent.inject
import server.StartApp
import server.modules.allModules


fun main() {
    startKoin {
        modules(allModules)
    }



    val app by inject<StartApp>(StartApp::class.java)
    app.start()


}