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

import io.ktor.client.*
import io.ktor.client.engine.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.auth.*
import io.ktor.client.plugins.auth.providers.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import io.ktor.client.plugins.HttpTimeout

fun createHttpClient(engine: HttpClientEngine): HttpClient {
    return HttpClient(engine) {
        // 1. Content Negotiation: Configure JSON serialization properly.
        install(ContentNegotiation) {
            json(
                Json {
                    // Corrected typo: 'ingnoreUnknownKeys' to 'ignoreUnknownKeys'
                    ignoreUnknownKeys = true
                    // Added: Makes the JSON output more readable (optional).
                    prettyPrint = true
                    // Added: Enforces strict JSON parsing (optional, but recommended).
                    isLenient = false
                }
            )
        }

        // 2. HttpTimeout: Set more descriptive timeout names and consider connect timeout.
        install(HttpTimeout) {
            // Added: Timeout for establishing a connection.
            connectTimeoutMillis = 10_000L // 10 seconds
            // Renamed: More descriptive names for clarity.
            socketTimeoutMillis = 20_000L // 20 seconds
            requestTimeoutMillis = 20_000L // 20 seconds
        }

        // 3. Auth: Improve token handling and consider environment variables.
        install(Auth) {
            // 3.1 Bearer Authentication:
            bearer {
                // Suggestion: Provide a way to load tokens dynamically.
                // Example: Using a token provider function.
                loadTokens {
                    // Replace this with your actual token loading logic.
                    // Example: Load from a secure storage or environment variable.
                    val accessToken = loadAccessTokenFromSecureStorage() // Replace with your logic
                    val refreshToken = loadRefreshTokenFromSecureStorage() // Replace with your logic
                    BearerTokens(accessToken, refreshToken)
                }
                // Suggestion: Provide a way to refresh tokens dynamically.
                refreshTokens {
                    // Replace this with your actual token refresh logic.
                    // Example: Call an API endpoint to refresh the token.
                    val oldTokens = this.oldTokens
                    val newAccessToken = refreshAccessToken(oldTokens?.refreshToken) // Replace with your logic
                    BearerTokens(newAccessToken, oldTokens?.refreshToken ?: "")
                }
            }

            // 3.2 Basic Authentication:
            basic {
                // Suggestion: Use environment variables or a configuration file.
                // This is more secure than hardcoding credentials.
                username = System.getenv("BASIC_AUTH_USERNAME") ?: "username" // Replace with your logic
                password = System.getenv("BASIC_AUTH_PASSWORD") ?: "password" // Replace with your logic
            }
        }

        // 4. Logging: Use a proper logger and consider log levels.
        install(Logging) {
            // Suggestion: Use a more descriptive logger name.
            logger = KtorLogger("KtorHttpClient")
            // Suggestion: Use a more appropriate log level.
            level = LogLevel.INFO // Or LogLevel.HEADERS, LogLevel.BODY, etc.
        }

        // 5. Default Request: Use the correct constant for JSON content type.
        defaultRequest {
            // Corrected: Use the constant from ContentType.Application.
            contentType(ContentType.Application.Json)
            // Added: Accept JSON responses by default.
            accept(ContentType.Application.Json)
        }
    }
}

// 6. Custom Logger Implementation
class KtorLogger(private val tag: String) : Logger {
    override fun log(message: String) {
        // Replace with your preferred logging mechanism (e.g., Timber, Logcat).
        println("[$tag] $message")
    }
}

// 7. Placeholder functions for token loading and refreshing
// Replace these with your actual implementation.
fun loadAccessTokenFromSecureStorage(): String {
    // Load the access token from secure storage (e.g., KeyStore).
    return "your_access_token"
}

fun loadRefreshTokenFromSecureStorage(): String {
    // Load the refresh token from secure storage.
    return "your_refresh_token"
}

fun refreshAccessToken(refreshToken: String?): String {
    // Call your API to refresh the access token using the refresh token.
    return "your_new_access_token"
}

//
//fun createHttpClient(engine: HttpClientEngine): HttpClient {
//    return HttpClient(engine) {
//        install(ContentNegotiation) {
//            json(
//                json = Json {
//                    ignoreUnknownKeys = true
//                    prettyPrint = true
//                    isLenient = false
//                }
//            )
//        }
//        install(HttpTimeout){
//            connectTimeoutMillis = 10_000L
//            socketTimeoutMillis = 20_000L
//            requestTimeoutMillis = 20_000L
//        }
//        install(Auth){
//            bearer {
//                loadTokens {}
//                refreshTokens {}
//            }
//
//            basic {
//                username = "username"
//                password = "password"
//            }
//        }
//
//        install(Logging){
//            logger = object : Logger {
//                override fun log(message: String){
//                    println(message)
//                }
//            }
//            level = LogLevel.ALL
//        }
//
//        defaultRequest {
//            contentType(ContentType.Application.Json)
//        }
//    }
//}