package core.network

import core.data.storage.Storage
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp

actual fun createPlatformHttpClient(storage: Storage): HttpClient =
    HttpClientFactory.create(OkHttp.create())