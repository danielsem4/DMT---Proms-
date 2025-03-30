package org.example.hit.heal.network


import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation

import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

expect class HttpClientFactory() {
    fun create(): HttpClient
}

fun createJsonConfig() = Json {
    prettyPrint = true
    isLenient = true
    ignoreUnknownKeys = true
}

