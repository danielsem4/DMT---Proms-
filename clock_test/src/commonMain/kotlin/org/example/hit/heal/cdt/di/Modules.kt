package org.example.hit.heal.cdt.di

import core.data.remote.impl.KtorAppRemoteDataSource
import core.domain.api.AppApi
import org.example.hit.heal.cdt.presentation.ClockTestViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val CDT_module = module {
    singleOf(::ClockTestViewModel)
    singleOf(::KtorAppRemoteDataSource).bind<AppApi>()
}
