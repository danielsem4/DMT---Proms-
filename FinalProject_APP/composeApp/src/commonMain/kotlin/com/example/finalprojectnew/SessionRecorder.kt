package com.example.finalprojectnew

import kotlinx.serialization.Serializable

@Serializable
enum class Stage1EndReason { COMPLETED, TIME_UP }

@Serializable
data class Stage1Result(
    val recipeId: String,
    val correctClicks: Int,
    val wrongClicks: Int,
    val elapsedMillis: Long,
    val endReason: Stage1EndReason
)

/** מחזיק בזיכרון את תוצאת שלב 1 עד שמסיימים את שלב 2. */
object SessionRecorder {
    @Volatile
    private var _stage1: Stage1Result? = null

    val stage1: Stage1Result? get() = _stage1

    fun recordStage1(result: Stage1Result) { _stage1 = result }

    fun clearStage1() { _stage1 = null }
}
