package org.example.hit.heal.core.di

import org.example.hit.heal.core.Network.AuthApi
import org.example.hit.heal.core.Network.AuthApiImpl
import org.koin.core.module.Module
import org.koin.dsl.module
import org.example.hit.heal.core.Network.HttpClientFactory
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.bind

//fun initKoin(config: KoinAppDeclaration? = null) =
//    startKoin {
//        config?.invoke(this)
//        modules(
//            sharedModule,
//            platformModule
//        )
//    }
//
//expect val platformModule: Module
//
//val sharedModule = module{
//    single { HttpClientFactory.createHttpClient(get()) }
//    singleOf(::AuthApiImpl).bind<AuthApi>()
//
//}
//





