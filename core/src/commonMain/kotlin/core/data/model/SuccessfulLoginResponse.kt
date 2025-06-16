package core.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SuccessfulLoginResponse(
    val token: String? = null,
    val user: User? = null,
)

@Serializable
data class LoginRequest(
    val email: String,
    val password: String
)





