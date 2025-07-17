package core.data.model.Medications

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class Medication(
    val id: Int,
    val patient: Int,
    val medicine: String,
    val name: String,
    val form: String,
    val unit: String,
    val frequency: String? = null,
    @SerialName("frequency_data") val frequencyData: String? = null,
    @SerialName("start_date") val startDate: String,
    @SerialName("end_date") val endDate: String? = null,
    val dosage: String
)