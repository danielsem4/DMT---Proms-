package core.data.model.evaluation

import kotlinx.serialization.Serializable

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