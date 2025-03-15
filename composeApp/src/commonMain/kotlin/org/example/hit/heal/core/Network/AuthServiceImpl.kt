package org.example.hit.heal.core.Network

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.HttpSend
import io.ktor.client.plugins.RedirectResponseException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.isSuccess
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json





class AuthServiceImpl(private val httpClient: HttpClient) : AuthApi {

    private val baseUrl = "https://generic2.hitheal.org.il/api/v1"

    override suspend fun login(loginRequest: LoginRequest): NetworkResult<SuccessfulLoginResponse> {
        return try {
            val response = httpClient.post("$baseUrl/login") { // Added /login to the base url
                contentType(ContentType.Application.Json)
                setBody(loginRequest)
            }

            if (response.status.isSuccess()) {
                val loginResponse = response.body<SuccessfulLoginResponse>()
                NetworkResult.Success(loginResponse)
            } else {
                NetworkResult.Error("Login failed with status code: ${response.status.value}")
            }
        } catch (e: RedirectResponseException) {
            // 3xx - responses
            NetworkResult.Error("Redirection Error: ${e.response.status.description}")
        } catch (e: ClientRequestException) {
            // 4xx - responses
            NetworkResult.Error("Client Error: ${e.response.status.description}")
        } catch (e: ServerResponseException) {
            // 5xx - responses
            NetworkResult.Error("Server Error: ${e.response.status.description}")
        } catch (e: Exception) {
            NetworkResult.Error("An unexpected error occurred: ${e.message}")
        }
    }
}
// Example of how to create the HttpClient
val client = HttpClient {
    install(HttpSend)
    install(ContentNegotiation) {
        json(Json {
            prettyPrint = true
            isLenient = true
            ignoreUnknownKeys = true
        })
    }
}
