package org.example.hit.heal.oriantation.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TrialData(
    @SerialName("measurement") var measurement: Int = 0,
    @SerialName("patient_id") var patientId: Int = 0,
    @SerialName("date") var date: String = "2030-12-12 12:12:12.6",
    @SerialName("clinicId") var clinicId: Int = 0,
    @SerialName("ObjectResponse") var response: ObjectResponse = ObjectResponse()
)
