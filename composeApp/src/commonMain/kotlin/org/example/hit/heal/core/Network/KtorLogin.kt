//package org.example.hit.heal.core.Network
//
//import io.ktor.client.plugins.ClientRequestException
//import io.ktor.client.plugins.RedirectResponseException
//import io.ktor.client.plugins.ServerResponseException
//import io.ktor.client.utils.EmptyContent.contentType
//import kotlinx.serialization.SerializationException
//
//// Define constants for better organization and maintainability
//private const val BASE_URL = "https://generic2.hitheal.org.il/api/v1/"
//private const val LOGIN_ENDPOINT = "auth/login"
//
//class KtorLogin(private val httpClient: com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.client.HttpClient) {
//
//    /**
//     * Attempts to log in a user with the provided credentials.
//     *
//     * @param loginRequest The user's login credentials.
//     * @return A [SuccessfulLoginResponse] if the login is successful, or null if an error occurs.
//     * @throws LoginException if there is an issue with the login process.
//     */
//    suspend fun login(loginRequest: LoginRequest): SuccessfulLoginResponse? {
//        return try {
//            val response = httpClient.post(urlString = "$BASE_URL$LOGIN_ENDPOINT") {
//                contentType(ContentType.Application.Json)
//                setBody(loginRequest)
//            }
//
//            // Check for HTTP status codes indicating success
//            if (response.status.isSuccess()) {
//                response.body<SuccessfulLoginResponse>()
//            } else {
//                // Handle non-successful HTTP status codes
//                throw LoginException("Login failed with status code: ${response.status.value}")
//            }
//        } catch (e: RedirectResponseException) {
//            // 3xx - responses
//            println("Redirect error during login: ${e.response.status.value} - ${e.message}")
//            throw LoginException("Redirect error during login", e)
//        } catch (e: ClientRequestException) {
//            // 4xx - responses
//            println("Client error during login: ${e.response.status.value} - ${e.message}")
//            throw LoginException("Client error during login", e)
//        } catch (e: ServerResponseException) {
//            // 5xx - responses
//            println("Server error during login: ${e.response.status.value} - ${e.message}")
//            throw LoginException("Server error during login", e)
//        } catch (e: SerializationException) {
//            println("Serialization error during login: ${e.message}")
//            throw LoginException("Serialization error during login", e)
//        } catch (e: Exception) {
//            // General exception handling
//            println("Error during login: ${e.message}")
//            throw LoginException("General error during login", e)
//        }
//    }