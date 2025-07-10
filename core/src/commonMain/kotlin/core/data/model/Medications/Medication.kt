package core.data.model.Medications

import kotlinx.serialization.Serializable

@Serializable
data class Medication(
    val id: Int,
    val patient_id: Int,
    val medicine_id: String,
    val name: String,
    val form: String,
    val unit: String,
    val frequency: String?,
    val frequency_data: String?,
    val start_date: String,
    val end_date: String?,
    val dosage: String
)
