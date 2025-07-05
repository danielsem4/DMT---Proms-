package org.example.hit.heal.di

import core.di.clientRequestsModule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import core.di.sessionModule
import di.Pass_module
import org.example.hit.heal.cdt.di.CDT_module
import org.example.hit.heal.hitber.di.Hitber_module
import org.example.hit.heal.home.HomeViewModel
import org.example.hit.heal.login.LoginViewModel
import org.example.hit.heal.splash.SplashViewModel
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.KoinAppDeclaration
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

val sharedAppModules = module {
    includes(clientRequestsModule)
    includes(sessionModule)
}

val sharedModules = module {
    single<CoroutineScope> { CoroutineScope(Dispatchers.Main + SupervisorJob()) }
    viewModelOf(::LoginViewModel)
    viewModelOf(::HomeViewModel)
    viewModelOf(::SplashViewModel)


    includes(Hitber_module)
    includes(Pass_module)
    includes(CDT_module)
}