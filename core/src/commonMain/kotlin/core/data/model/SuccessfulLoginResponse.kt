package core.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SuccessfulLoginResponse(
//    val token: String,
//    val user: User
    val refresh: String,
    val access: String,
    @SerialName("userId") val userId: String,
    val email: String,
    val image: String,
    @SerialName("research_patient") val researchPatient: Boolean,
    @SerialName("first_name") val firstName: String,
    @SerialName("full_name") val fullName: String,
    val clinicId: Int,
    val clinicName: String,
    val token: String,
    val modules: List<String>,
    val status: String,
    @SerialName("server_url") val serverUrl: String
)

@Serializable
data class LoginRequest(val email: String, val password: String)
