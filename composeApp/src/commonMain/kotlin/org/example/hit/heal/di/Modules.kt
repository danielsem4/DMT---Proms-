package org.example.hit.heal.di


import MedicationViewModel
import com.example.new_memory_test.di.MemoryModule
import com.example.new_memory_test.presentation.ViewModel.ViewModelMemoryTest
import core.di.AudioModule
import core.di.clientRequestsModule
import core.di.sessionModule
import di.Pass_module
import org.example.hit.heal.cdt.di.CDT_module

import org.example.hit.heal.hitber.di.Hitber_module

import org.example.hit.heal.presentation.activities.ActivitiesViewModel
import org.example.hit.heal.presentation.evaluation.EvaluationTestViewModel
import org.example.hit.heal.presentation.evaluation.EvaluationsViewModel

import org.example.hit.heal.presentation.home.HomeViewModel
import org.example.hit.heal.presentation.login.LoginViewModel
import org.example.hit.heal.presentation.splash.SplashViewModel
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

val sharedAppModules = module{
    includes(clientRequestsModule)
    includes(sessionModule)
    includes(AudioModule)

}

val sharedModules = module {
    viewModelOf(::LoginViewModel)
    viewModelOf(::HomeViewModel)
    viewModelOf(::SplashViewModel)


    includes(MemoryModule)
    includes(Hitber_module)
    includes(Pass_module)
    viewModelOf(::EvaluationsViewModel)

    viewModelOf(::ActivitiesViewModel)
    viewModelOf(::EvaluationsViewModel)
    viewModelOf(::EvaluationTestViewModel)

    viewModelOf(::MedicationViewModel)


    includes(CDT_module)
}