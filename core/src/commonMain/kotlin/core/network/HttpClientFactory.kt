package core.network

import core.data.storage.Storage
import core.domain.session.TokenProvider
import core.util.PrefKeys
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpSend
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.cache.HttpCache
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
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
    fun create(
        engine: HttpClientEngine,
        storage: Storage
    ): HttpClient = HttpClient(engine) {
        expectSuccess = false

        install(ContentNegotiation) {
            json(
                json = Json {
                    ignoreUnknownKeys = true
                    prettyPrint = true
                    isLenient = true
                    encodeDefaults = true
                }
            )
        }
        install(HttpTimeout) {
            socketTimeoutMillis = 20_000
            requestTimeoutMillis = 20_000
        }
        install(HttpCache)
        install(Logging) {
            logger = object : Logger {
                override fun log(message: String) {
                    println(message)
                }
            }
            level = LogLevel.ALL
        }

        install(DefaultRequest) {
            runBlocking {
                storage.get(PrefKeys.token)?.let {
                    header(HttpHeaders.Authorization, "Bearer $it")
                }
            }
            contentType(ContentType.Application.Json)
        }
    }
}

//object HttpClientFactory {
//    fun create(engine: HttpClientEngine): HttpClient {
//        return HttpClient(engine) {
//            expectSuccess = false
//            install(ContentNegotiation) {
//                json(
//                    json = Json {
//                        ignoreUnknownKeys = true
//                        prettyPrint = true
//                        isLenient = true
//                        encodeDefaults = true
//                    }
//                )
//            }
//            install(HttpTimeout) {
//                socketTimeoutMillis = 20_000
//                requestTimeoutMillis = 20_000
//            }
//            install(HttpCache)
//            install(Logging) {
//                logger = object : Logger {
//                    override fun log(message: String) {
//                        println(message)
//                    }
//                }
//                level = LogLevel.ALL
//            }
//            defaultRequest {
//                contentType(ContentType.Application.Json)
//            }
//        }
//    }
//}