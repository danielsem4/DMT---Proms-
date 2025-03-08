package org.example.hit.heal.core.Data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@kotlinx.serialization.Serializable
class SuccessfullLoginResponse {
    val refresh: String? = null,
    val access: String? = null,
    @SerialName("userId") val userId: String? = null,
    val email: String? = null,
    val image: String? = null,
    val research_patient: Boolean? = null,
    val first_name: String? = null,
    val full_name: String? = null,
    val clinicId: Int? = null,
    val clinicName: String? = null,
    val token: String? = null,
    val modules: List<String> = emptyList(),
    val status: String? = null,
    val server_url: String? = null
    )

    @Serializable
    data class ModuleResponse(
        val modules: List<String>
    )
}