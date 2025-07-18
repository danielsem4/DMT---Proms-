package core.network

import core.data.storage.Storage
import io.ktor.client.HttpClient
import io.ktor.client.engine.darwin.Darwin

actual fun createPlatformHttpClient(storage: Storage): HttpClient =
    HttpClientFactory.create(Darwin.create())