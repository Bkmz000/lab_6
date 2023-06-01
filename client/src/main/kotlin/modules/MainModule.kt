package client.modules

import client.StartApp
import AllExecuteSamples
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val commandModule = module {
    singleOf(::AllExecuteSamples)

}


val collectionModule = module {

}

val appModule = module {
    singleOf(::StartApp)
}


val allModules = module {
    includes(collectionModule, appModule, commandModule)
}