package core.data.model.evaluation

import kotlinx.serialization.Serializable

@Serializable
data class EvaluationObject(
    val id: Int,
    val object_label: String,
    val measurement_screen: Int,
    val measurement_order: Int,
    val return_value: Boolean,
    val number_of_values: Int,
    val predefined_values: Boolean,
    val random_selection: Boolean,
    val order_important: Boolean,
    val show_icon: Boolean,
    var answer: String,
    val style: String?,
    val is_grade: Boolean,
    val measurement_id: Int? = null,
    val object_type: Int,
    val language: Int,
    val available_values: List<EvaluationValue>? = null
)
