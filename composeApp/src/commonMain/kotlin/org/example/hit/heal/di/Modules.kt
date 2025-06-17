package org.example.hit.heal.di

import core.di.clientRequestsModule
import core.di.sessionModule
import org.example.hit.heal.cdt.di.CDT_module
import org.example.hit.heal.hitber.presentation.ActivityViewModel
import org.example.hit.heal.hitber.presentation.buildShape.TenthQuestionViewModel
import org.example.hit.heal.hitber.presentation.concentration.ThirdQuestionViewModel
import org.example.hit.heal.hitber.presentation.dragAndDrop.SeventhQuestionViewModel
import org.example.hit.heal.hitber.presentation.naming.FourthQuestionViewModel
import org.example.hit.heal.hitber.presentation.shapes.SecondQuestionViewModel
import org.example.hit.heal.hitber.presentation.timeAndPlace.FirstQuestionViewModel
import org.example.hit.heal.hitber.presentation.understanding.SixthQuestionViewModel
import org.example.hit.heal.hitber.presentation.writing.EightQuestionViewModel
import org.example.hit.heal.login.LoginViewModel
import org.koin.core.context.startKoin
import org.example.hit.heal.Home.HomeViewModel
import org.example.hit.heal.splash.SplashViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModel
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
            platformModule,
        )
    }

expect val platformModule: Module

val sharedAppModules = module{
    includes(clientRequestsModule)
    includes(sessionModule)
}

val sharedModules = module {
    viewModelOf(::LoginViewModel)
    viewModelOf(::HomeViewModel)
    viewModelOf(::SplashViewModel)

    single { ActivityViewModel(get(), get(), get()) }
    viewModel { FirstQuestionViewModel() }
    single { SecondQuestionViewModel() }
    viewModel { ThirdQuestionViewModel() }
    viewModel { FourthQuestionViewModel() }
    viewModel { SixthQuestionViewModel() }
    viewModel { SeventhQuestionViewModel() }
    viewModel { EightQuestionViewModel() }
    viewModel { TenthQuestionViewModel() }


    includes(CDT_module)
}