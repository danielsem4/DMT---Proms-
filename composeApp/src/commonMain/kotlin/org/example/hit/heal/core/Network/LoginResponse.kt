package org.example.hit.heal.core.Network

import kotlinx.serialization.Serializable

class LoginResponse {

    @Serializable
    data class LoginResponse(
        val success: Boolean,
        val token: String? = null,
        val message: String? = null
        // Add other relevant fields from the API response
    )
}