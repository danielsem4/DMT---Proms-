package org.example.hit.heal.di

import org.example.hit.heal.network.AuthApi
import org.example.hit.heal.network.AuthApiImpl
import org.example.hit.heal.network.HttpClientFactory
import org.koin.dsl.module

val networkModule = module {
    single { HttpClientFactory().create() }
    single<AuthApi> { AuthApiImpl(get()) }
} 