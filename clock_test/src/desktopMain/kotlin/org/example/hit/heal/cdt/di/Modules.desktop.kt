package org.example.hit.heal.cdt.di

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp
import org.koin.dsl.module

actual val platformModule = module {
    single<HttpClientEngine> { OkHttp.create() }
}