package org.example.hit.heal.core.domain

import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.basic
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.utils.EmptyContent.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json


fun createHttpClient(engine: HttpClientEngine): HttpClient {
    return HttpClient(engine) {
        install(ContentNegotiation) {
            json(
                json = Json {
                    ingnoreUnknownKeys = true
                }
            )
        }
        install(HttpTimeout){
            socketTimeoutMillis = 20_000L
            requestTimeoutMillis = 20_000L
        }
        install(Auth){
            bearer {
                loadTokens {}
                refreshTokens {}
            }

            basic {
                username = "username"
                password = "password"
            }
        }

        install(Logging){
            logger = object : Logger {
                override fun log(message: String){
                    println(message)
                }
            }
            level = LogLevel.ALL
        }

        defaultRequest {
            contentType(ContentType.Application.Json)
        }
    }
}