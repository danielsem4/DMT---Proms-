package core.network

import core.data.storage.Storage
import core.util.PrefKeys
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.cache.HttpCache
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.observer.ResponseObserver
import io.ktor.client.request.accept
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json

/**
 * Factory for HttpClient settings
 */
object HttpClientFactory {
    fun create(engine: HttpClientEngine, storage: Storage): HttpClient =
        HttpClient(engine) {
            expectSuccess = false

            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    isLenient       = true
                    prettyPrint     = true
                    encodeDefaults  = true
                })
            }

            install(HttpTimeout) {
                socketTimeoutMillis  = 20_000
                requestTimeoutMillis = 20_000
            }

            install(HttpCache)

            // Dump full request & response bodies
            install(Logging) {
                logger = object : Logger { override fun log(msg: String) = println(msg) }
                level  = LogLevel.BODY
            }

            // Always see status codes + URL
            install(ResponseObserver) {
                onResponse { resp ->
                    println("[HTTP ${resp.status.value}] ${resp.call.request.url}")
                }
            }

            install(DefaultRequest) {
                contentType(ContentType.Application.Json)
                accept(ContentType.Application.Json)
            }
        }
}