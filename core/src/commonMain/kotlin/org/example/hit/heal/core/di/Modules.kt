package org.example.hit.heal.core.di

import io.ktor.client.HttpClient
import org.example.hit.heal.core.Network.AuthApi
import org.example.hit.heal.core.Network.AuthApiImpl
import org.koin.core.module.Module
import org.koin.dsl.module

import org.example.hit.heal.core.Network.HttpClientFactory

expect val platformModule: Module

val sharedModule = module{
   single {HttpClientFactory.createHttpClient(get())}
    single<AuthApi> { AuthApiImpl(get()) }
    }



