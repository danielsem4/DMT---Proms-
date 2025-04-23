@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package org.example.hit.heal.cdt.di

import io.ktor.client.HttpClient
import io.ktor.client.engine.darwin.Darwin
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.example.hit.heal.cdt.domain.TokenProvider

actual object HttpClientFactory {
    actual fun create(): HttpClient = HttpClient(Darwin) {
        install(ContentNegotiation) {
            json(Json { ignoreUnknownKeys = true; isLenient = true })
        }
        defaultRequest {
            contentType(ContentType.Application.Json)
            headers.append("token", TokenProvider.getCurrentToken())
        }
    }
}