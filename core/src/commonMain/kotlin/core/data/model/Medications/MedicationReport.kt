package core.data.model.Medications

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MedicationReport(
    @SerialName("clinic_id") val clinicId: Int,
    @SerialName("patient_id") val patientId: Int,
    @SerialName("medication_id") val medicationId: Long,
    val timestamp: String
)

@Serializable
data class MedicationNotificationData(
    @SerialName("clinic_id")
    val clinicId: Int,

    @SerialName("patient_id")
    val patientId: Int,

    @SerialName("medication_id")
    val medicationId: Long,

    val frequency: String,

    @SerialName("start_date")
    val startDate: String,

    @SerialName("end_date")
    val endDate: String,

    @SerialName("start_time")
    val startTime: String,

    val interval: Int
)