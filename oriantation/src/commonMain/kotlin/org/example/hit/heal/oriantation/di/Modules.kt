package org.example.hit.heal.oriantation.di

import core.data.remote.impl.KtorAppRemoteDataSource
import core.domain.api.AppApi
import org.example.hit.heal.oriantation.data.model.OrientationTestViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val ORIENTATION_module = module {
    singleOf(::OrientationTestViewModel)
    singleOf(::KtorAppRemoteDataSource).bind<AppApi>()
} 