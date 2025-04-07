//package org.example.hit.heal.network
//
//import io.ktor.client.HttpClient
//import io.ktor.client.call.body
//import io.ktor.client.request.post
//import io.ktor.client.request.setBody
//import io.ktor.http.ContentType
//import io.ktor.http.HttpStatusCode
//import io.ktor.http.contentType
//
//class AuthApiImpl(private val client: HttpClient) : AuthApi {
//    private val baseUrl = "https://generic2.hitheal.org.il/api/v1"
//
//    override suspend fun login(email: String, password: String): Result<SuccessfulLoginResponse> {
//        return try {
//            val response = client.post("$baseUrl/login/") {
//                contentType(ContentType.Application.Json)
//                setBody(LoginRequest(email, password))
//            }
//
//            when (response.status) {
//                HttpStatusCode.OK -> {
//                    val loginResponse = response.body<SuccessfulLoginResponse>()
//                    if (loginResponse.status == "Success") {
//                        Result.success(loginResponse)
//                    } else {
//                        Result.failure(Exception(loginResponse.status ?: "Login failed"))
//                    }
//                }
//                HttpStatusCode.Unauthorized -> {
//                    Result.failure(Exception("Invalid email or password"))
//                }
//                else -> {
//                    Result.failure(Exception("Server error: ${response.status}"))
//                }
//            }
//        } catch (e: Exception) {
//            Result.failure(Exception("Network error: ${e.message}"))
//        }
//    }
//}