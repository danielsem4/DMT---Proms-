package core.data.model.evaluation

import kotlinx.serialization.Serializable

@Serializable
data class EvaluationSettings(
    val id: Int = 0,
    val measurement_repeat_period: String = "",
    val measurement_repeat_times: Float = 0f,
    val measurement_begin_time: String = "",
    val measurement_last_time: String? = "",
    val measurement_end_time: String? = "",
    val is_repetitive: Boolean = false,
    val times_taken: Int = 0,
    val patient: Int = 0,
    val doctor: Int = 0,
    val clinic: Int = 0,
    val measurement: Int = 0
)