package org.example.hit.heal.cdt.di

import androidx.lifecycle.SavedStateHandle
import org.example.hit.heal.cdt.data.network.ClockRepositoryImpl
import org.example.hit.heal.cdt.data.network.SendCDTUseCase
import org.example.hit.heal.cdt.domain.ClockRepository
import org.example.hit.heal.cdt.domain.UploadCDTResultsUseCase
import org.example.hit.heal.cdt.presentation.ClockTestViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

expect val platformModule: Module

val CDT_module = module {
    viewModelOf(::ClockTestViewModel)
    factory { SavedStateHandle() }
    singleOf(::ClockRepositoryImpl).bind<ClockRepository>()
    single { SendCDTUseCase(get()) }
    single { HttpClientFactory.create() }

    // Use-case as singleton
    singleOf(::UploadCDTResultsUseCase)
}
