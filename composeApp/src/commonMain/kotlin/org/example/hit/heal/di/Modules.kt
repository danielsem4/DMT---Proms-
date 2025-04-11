package org.example.hit.heal.di

import org.example.hit.heal.core.Network.AuthApi
import org.example.hit.heal.core.Network.AuthApiImpl
import org.example.hit.heal.core.Network.HttpClientFactory
import org.example.hit.heal.core.di.clientRequestsModule
import org.example.hit.heal.login.LoginViewModel
import org.example.hit.heal.navigation.NavigationViewModel
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.bind
import org.koin.dsl.module

fun initKoin(config: KoinAppDeclaration? = null) =
    startKoin {
        config?.invoke(this)
        modules(
            sharedModules,
            sharedAppModules,
            platformModule
        )
    }

expect val platformModule: Module

val sharedAppModules = module{
    includes(clientRequestsModule)

}

val sharedModules = module {
    viewModelOf(::LoginViewModel)
    single { NavigationViewModel() }

}