package server.modules

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import server.StartApp
import server.collection.ProductCollection


val commandModule = module {

}


val collectionModule = module {
    singleOf(::ProductCollection)
}

val appModule = module {
    singleOf(::StartApp)
}


val allModules = module {
    includes(collectionModule, appModule, commandModule)
}
