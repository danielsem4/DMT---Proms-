package core.network

import core.data.storage.Storage
import io.ktor.client.HttpClient

expect fun createPlatformHttpClient(storage: Storage): HttpClient
