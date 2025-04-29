package org.example.hit.heal.cdt.di

import androidx.lifecycle.SavedStateHandle
import org.example.hit.heal.cdt.data.network.CDTRepositoryImpl
import org.example.hit.heal.cdt.data.network.SendCDTUseCase
import org.example.hit.heal.cdt.domain.CDTRepository
import org.example.hit.heal.cdt.presentation.ClockTestViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.bind
import org.koin.dsl.module

expect val platformModule: Module

val CDT_module = module {
    viewModel { ClockTestViewModel(get(),get()) }
    factory { SavedStateHandle() }
    singleOf(::CDTRepositoryImpl).bind<CDTRepository>()
    single { SendCDTUseCase(get()) }
    single { HttpClientFactory.create() }
}
