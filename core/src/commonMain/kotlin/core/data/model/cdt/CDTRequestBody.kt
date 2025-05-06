package core.data.model.cdt

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CDTRequestBody(
    @SerialName("measurement") var measurement: Int = 0,
    @SerialName("patient_id") var patientId: Int = 0,
    @SerialName("date") var date: String = "2030-12-12 12:12:12.6",
    @SerialName("clinicId") var clinicId: Int = 0,
    @SerialName("test") var test: CDTResults = CDTResults(),
)