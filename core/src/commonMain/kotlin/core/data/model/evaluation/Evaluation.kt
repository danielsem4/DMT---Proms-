package core.data.model.evaluation

import kotlinx.serialization.Serializable

@Serializable
data class Evaluation(
    val id: Int,
    val measurement_name: String,
    val display_as_module: Boolean,
    val is_multilingual: Boolean,
    val is_active: Boolean,
    val measurement_settings: EvaluationSettings,
    val measurement_objects: List<EvaluationObject>,
)
