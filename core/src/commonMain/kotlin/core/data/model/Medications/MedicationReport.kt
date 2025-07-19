package core.data.model.Medications

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MedicationReport(
     val clinicId: Int,
     @SerialName("patient_id")val patientId: Int,
     @SerialName("medication_id")val medicationId: Int,
      val timestamp: String
)