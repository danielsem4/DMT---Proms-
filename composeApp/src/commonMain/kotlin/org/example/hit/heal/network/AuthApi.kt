package org.example.hit.heal.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

interface AuthApi {
    suspend fun login(email: String, password: String): Result<SuccessfulLoginResponse>
}

@Serializable
data class LoginRequest(
    val email: String,
    val password: String
)

@Serializable
data class SuccessfulLoginResponse(
    val refresh: String? = null,
    val access: String? = null,
    @SerialName("userId") val userId: String? = null,
    val email: String? = null,
    val image: String? = null,
    @SerialName("research_patient") val researchPatient: Boolean? = null,
    @SerialName("first_name") val firstName: String? = null,
    @SerialName("full_name") val fullName: String? = null,
    val clinicId: Int? = null,
    val clinicName: String? = null,
    val token: String? = null,
    val modules: List<String> = emptyList(),
    val status: String? = null,
    @SerialName("server_url") val serverUrl: String? = null
)

@Serializable
data class ModuleResponse(
    val modules: List<String> = emptyList()
) 