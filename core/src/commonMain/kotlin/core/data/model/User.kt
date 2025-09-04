package core.data.model

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class User(
    val id: Int = 1,
    val password: String? = "null",
    val last_login: String? = "null",
    val is_superuser: Boolean? = false,
    val is_staff: Boolean? = false,
    val is_active: Boolean? = false,
    val date_joined: String? = "null",
    val email: String? = "null",
    val phone_number: String? = "null",
    val first_name: String? = "null",
    val last_name: String? = "null",
    val is_clinic_manager: Boolean? = false,
    val is_doctor: Boolean? = false,
    val is_patient: Boolean? = false,
    val is_research_patient: Boolean? = false,
    val groups: List<String> = emptyList(),
    val user_permissions: List<String> = emptyList(),
    val clinicId: Int = 1,
    val clinicName: String? = "null",
    val modules: List<List<JsonElement>> = emptyList(),
    val research_patient: Boolean? = false,
    val status: String? = "null",
    val server_url: String? = "null",
    val hospital_image: String? = ""
)

@Serializable
data class ModulesResponse(
    var module_name: String = "",
    val module_id: Int = 0,
)
