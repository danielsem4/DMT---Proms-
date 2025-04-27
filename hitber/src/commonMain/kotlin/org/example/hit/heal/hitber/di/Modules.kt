package org.example.hit.heal.hitber.di


import org.example.hit.heal.hitber.ActivityViewModel
import org.example.hit.heal.hitber.core.data.HttpClientFactory
import org.example.hit.heal.hitber.data.ImageUploadRepository
import org.example.hit.heal.hitber.data.network.ImageUploadApi
import org.example.hit.heal.hitber.presentation.ImageUploadViewModel
import org.koin.core.module.Module
import org.koin.dsl.module

expect val platformModule: Module

val appModule: Module = module {

    single { HttpClientFactory.createHttpClient(get()) }
    single { ImageUploadApi(get()) }
    single { ImageUploadRepository(get()) }
    single { ImageUploadViewModel(get()) }
    single { ActivityViewModel() }
}
