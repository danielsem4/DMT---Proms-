package core.data.model

data class ActivityItem(
    val id: Int,
    val name: String,
    val description: String?,
)

data class ActivityItemReport(
    val clinic_id: Int,
    val patient_id: Int,
    val activity_id: Int,
    val date: String
)
