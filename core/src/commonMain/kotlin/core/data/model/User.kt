package core.data.model


data class User(
    val id: Int = 1,
    val password: String? = null,
    val last_login: String? = null,
    val is_superuser: Boolean? = null,
    val is_staff: Boolean? = null,
    val is_active: Boolean? = null,
    val date_joined: String? = null,
    val email: String? = null,
    val phone_number: String? = null,
    val first_name: String? = null,
    val last_name: String? = null,
    val is_clinic_manager: Boolean? = null,
    val is_doctor: Boolean? = null,
    val is_patient: Boolean? = null,
    val is_research_patient: Boolean? = null,
    val groups: List<String> = emptyList(),
    val user_permissions: List<String> = emptyList(),
    val clinicId: Int = 1,
    val clinicName: String? = null,
    val modules: List<ModulesResponse> = emptyList(),
    val research_patient: Boolean? = null,
    val status: String? = null,
    val server_url: String? = "null",
    val hospital_image: String? = ""
)

data class ModuleResponse(
    val modules: ArrayList<String>
)

data class ModulesResponse(
    val module_name: String = "",
    val module_id: Int = 0
)
