package core.data.model.Medications

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class Medication(
    val id: Int,
    @SerialName("clinic_id") val clinicId: Int? = null,
    @SerialName("patient") val patient: Int,
    @SerialName("patient_id") val patientId: Int? = null,
    @SerialName("medication_id") val medicationId: Int? = null,
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