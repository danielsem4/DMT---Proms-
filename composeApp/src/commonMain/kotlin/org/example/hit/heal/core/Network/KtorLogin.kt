//package org.example.hit.heal.core.Network
//
//import io.ktor.client.HttpClient
//import io.ktor.client.call.body
//import io.ktor.client.request.post
//import io.ktor.client.request.setBody
//import io.ktor.http.ContentType
//import io.ktor.http.contentType
//import org.example.hit.heal.core.Model.LoginRequest
//import org.example.hit.heal.core.Model.SuccessfulLoginResponse
//
//private const val BASE_URL = "https://generic2.hitheal.org.il/api/v1/"
//private const val LOGIN_ENDPOINT = "auth/login"
//class KtorLogin (private val httpClient: HttpClient){
//    suspend fun login(loginRequest: LoginRequest): SuccessfulLoginResponse? {
//        return try {
//            httpClient.post(urlString = "${BASE_URL}auth/login") {
//                contentType(ContentType.Application.Json)
//                setBody(loginRequest)
//            }.body<SuccessfulLoginResponse>()
//        } catch (e: Exception) {
//            // Handle exceptions appropriately, e.g., logging, error handling
//            println("Error during login: ${e.message}")
//            null
//        }
//    }
//
//}