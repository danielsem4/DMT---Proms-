package org.example.hit.heal.di

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp
import org.koin.core.module.Module
import org.koin.dsl.module
import core.di.platformModuleCore

actual val platformModule: Module
    get() = module {
        single<HttpClientEngine> { OkHttp.create() }
        includes(platformModuleCore)
    }