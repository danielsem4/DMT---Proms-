package org.example.hit.heal.core.di

import org.example.hit.heal.core.Network.AuthApi
import org.example.hit.heal.core.Network.AuthApiImpl
import org.koin.dsl.module
import org.example.hit.heal.core.Network.HttpClientFactory
import org.example.hit.heal.core.Network.session.SessionManager
import org.example.hit.heal.core.Network.session.SessionManagerImpl
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind


val clientRequestsModule = module{
    single { HttpClientFactory.createHttpClient(get()) }
    singleOf(::AuthApiImpl).bind<AuthApi>()
    single<SessionManager> { SessionManagerImpl() }

}






