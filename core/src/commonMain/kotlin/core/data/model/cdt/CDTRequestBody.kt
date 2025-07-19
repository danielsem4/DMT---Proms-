package core.data.model.cdt

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CDTRequestBody(
    @SerialName("measurement") var measurement: Int,
    @SerialName("patient_id") var patientId: String,
    @SerialName("date") var date: String,
    @SerialName("clinicId") var clinicId: Int,
    @SerialName("test") var test: CDTResults,
)