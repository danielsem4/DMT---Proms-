package org.example.hit.heal.di

import core.di.clientRequestsModule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import core.di.sessionModule
import org.example.hit.heal.cdt.di.CDT_module
import org.example.hit.heal.login.LoginViewModel
import org.koin.core.context.startKoin
import org.example.hit.heal.Home.HomeViewModel
import org.example.hit.heal.splash.SplashViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.bind
import org.koin.dsl.module
import presentation.appsDeviceScreen.AppDeviceViewModel
import presentation.appsDeviceScreen.WrongAppViewModel
import core.domain.audio.AudioPlayer
import core.domain.use_case.PlayAudioUseCase
import presentation.components.CountdownDialogHandler
import presentation.components.CountdownTimerUseCase
import presentation.contatcts.ContactsViewModel
import presentation.detailedContact.DetailedContactScreen
import presentation.detailedContact.DetailedContactViewModel
import presentation.dialScreen.DialScreenViewModel
import presentation.entryScreen.EntryViewModel
import presentation.nextQuestion.NextQuestionViewModel

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
    includes(sessionModule)
}

val sharedModules = module {
    single<CoroutineScope> { CoroutineScope(Dispatchers.Main + SupervisorJob()) }
    viewModelOf(::LoginViewModel)
    viewModelOf(::HomeViewModel)
    viewModelOf(::SplashViewModel)

  //  single { NavigationViewModel() }
//    viewModelOf(::NavigationViewModel) //should be like this instead of single


    single { CountdownTimerUseCase(get()) }
    single { CountdownDialogHandler(get()) }
    single { AudioPlayer() }
    single { PlayAudioUseCase(get()) }
    single { EntryViewModel(get()) }
    single { AppDeviceViewModel(get(), get()) }
    single { WrongAppViewModel(get(), get()) }
    single { ContactsViewModel(get(), get()) }
    single { DetailedContactViewModel(get(), get()) }
    single { NextQuestionViewModel(get()) }
    single { DialScreenViewModel(get(), get()) }
    includes(CDT_module)
}