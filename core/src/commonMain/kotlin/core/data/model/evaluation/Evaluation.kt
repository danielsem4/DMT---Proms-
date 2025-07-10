package core.data.model.evaluation

import kotlinx.serialization.Serializable

/**
 *
 */

@Serializable
data class Evaluation(
    val id: Int,
    val measurement_name: String,
    val display_as_module: Boolean,
    val is_multilingual: Boolean,
    val is_active: Boolean,
    val measurement_type: String? = null,
    val measurement_settings: EvaluationSettings,
    val measurement_objects: List<EvaluationObject?>,
)

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

@Serializable
data class EvaluationSettings(
    val id: Int,
    val measurement_repeat_period: String,
    val measurement_repeat_times: Int,
    val measurement_begin_time: String,
    val measurement_last_time: String,
    val measurement_end_time: String?,
    val is_repetitive: Boolean,
    val times_taken: Int,
    val patient: Int,
    val doctor: Int,
    val clinic: Int,
    val measurement: Int
)

@Serializable
data class EvaluationValue(
    val id: Int,
    val available_value: String,
    val default_value: Boolean,
    val object_address: String,
    val measurementObject_id: Int
)
