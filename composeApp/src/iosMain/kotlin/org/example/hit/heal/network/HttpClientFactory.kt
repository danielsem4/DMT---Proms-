//package org.example.hit.heal.network
//
//import io.ktor.client.HttpClient
//import io.ktor.client.engine.darwin.Darwin
//import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
//import io.ktor.serialization.kotlinx.json.json
//
//actual class HttpClientFactory {
//    actual fun create(): HttpClient = HttpClient(Darwin) {
//        install(ContentNegotiation) {
//            json(createJsonConfig())
//        }
//    }
//}