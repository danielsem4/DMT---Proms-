package core.di

import core.data.remote.impl.KtorAppRemoteDataSource
import core.data.storage.DataStoreStorage
import core.data.storage.Storage
import core.domain.api.AppApi
import core.domain.use_case.BitmapToUploadUseCase
import core.domain.use_case.LoginUseCase
import core.domain.use_case.PlayAudioUseCase
import core.domain.use_case.cdt.UploadFileUseCase
import core.domain.use_case.cdt.UploadTestResultsUseCase
import core.network.HttpClientFactory
import core.utils.AudioPlayer
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module


/**
 * All shared dependencies go here
 * dependencies that can be used in different modules
 */

val clientRequestsModule = module {
    single { HttpClientFactory.create(get()) }
    singleOf(::KtorAppRemoteDataSource).bind<AppApi>()
    singleOf(::LoginUseCase)
    singleOf(::UploadFileUseCase)
    singleOf(::UploadTestResultsUseCase)
    single { BitmapToUploadUseCase() }
}

val AudioModule = module {
    single { AudioPlayer() }
    single { PlayAudioUseCase(get()) }
}


val sessionModule = module {
    single<Storage> {DataStoreStorage(get())}
}

expect val platformModuleCore: Module