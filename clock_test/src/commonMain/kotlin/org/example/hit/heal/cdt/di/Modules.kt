package org.example.hit.heal.cdt.di

import org.example.hit.heal.cdt.data.network.CDTRepositoryImpl
import org.example.hit.heal.cdt.domain.CDTRepository
import org.example.hit.heal.cdt.presentation.ClockTestViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

expect val platformModule: Module

val CDT_module = module {
    singleOf(::ClockTestViewModel)

    singleOf(::CDTRepositoryImpl).bind<CDTRepository>()

    single { HttpClientFactory.create() }
}
