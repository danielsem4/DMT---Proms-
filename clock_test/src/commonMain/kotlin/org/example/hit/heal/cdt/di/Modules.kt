package org.example.hit.heal.cdt.di

import androidx.lifecycle.SavedStateHandle
import org.example.hit.heal.cdt.data.network.ClockRepositoryImpl
import org.example.hit.heal.cdt.domain.ClockRepository
import org.example.hit.heal.cdt.domain.UploadCDTResultsUseCase
import org.example.hit.heal.cdt.domain.UploadImageUseCase
import org.example.hit.heal.cdt.presentation.ClockTestViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val CDT_module = module {
    singleOf(::UploadImageUseCase)
    singleOf(::UploadCDTResultsUseCase)
    singleOf(::ClockTestViewModel)
    factory { SavedStateHandle() }
    singleOf(::ClockRepositoryImpl).bind<ClockRepository>()
}
