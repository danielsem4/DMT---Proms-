package core.data.model.evaluation

import core.data.model.MeasureObjectString
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EvaluationRequestBody(
    @SerialName("measurement") var measurement: Int,
    @SerialName("patient_id") var patientId: Int,
    @SerialName("date") var date: String,
    @SerialName("clinicId") var clinicId: Int,
    @SerialName("results") var evaluationResults: List<MeasureObjectString> = arrayListOf(),
)