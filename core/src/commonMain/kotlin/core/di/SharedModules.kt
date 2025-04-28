package core.di

import core.data.remote.impl.KtorAppRemoteDataSource
import core.domain.api.AppApi
import core.domain.use_case.LoginUseCase
import core.network.HttpClientFactory
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module


/**
 * All shared dependencies go here
 * dependencies that can be used in different modules
 */

val clientRequestsModule = module{
    single { HttpClientFactory.create(get()) }
    singleOf(::KtorAppRemoteDataSource).bind<AppApi>()
    singleOf(::LoginUseCase)

}