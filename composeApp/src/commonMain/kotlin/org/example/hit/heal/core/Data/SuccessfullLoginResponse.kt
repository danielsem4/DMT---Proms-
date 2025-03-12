package org.example.hit.heal.core.Data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


    @Serializable
    data class SuccessfullLoginResponse(
        @SerialName("refresh")
        val refreshToken: String? = null,

        @SerialName("access")
        val accessToken: String? = null,

        @SerialName("userId")
        val userId: String? = null,

        @SerialName("email")
        val email: String? = null,

        @SerialName("image")
        val profileImageUrl: String? = null,

        @SerialName("research_patient")
        val isResearchPatient: Boolean? = null,

        @SerialName("first_name")
        val firstName: String? = null,

        @SerialName("full_name")
        val fullName: String? = null,

        @SerialName("clinicId")
        val clinicId: Int? = null,

        @SerialName("clinicName")
        val clinicName: String? = null,

        @SerialName("token")
        val deviceToken: String? = null,

        @SerialName("modules")
        val modules: List<String> = emptyList(),

        @SerialName("status")
        val status: String? = null,

        @SerialName("server_url")
        val serverUrl: String? = null
    )

    @Serializable
    data class ModuleResponse(
        val modules: List<String>
    )
