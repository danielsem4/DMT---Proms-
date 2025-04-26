package org.example.hit.heal.hitber.di

import io.ktor.client.engine.HttpClientEngine
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module
    get() = module {
        single<HttpClientEngine> { io.ktor.client.engine.okhttp.OkHttp.create() }

    }