package com.example.finalprojectnew.assessment

// Serializable make the convert to JSON
import kotlinx.serialization.Serializable

// stage1 result
@Serializable
data class Stage1Result(
    val recipeId: String,
    val correctClicks: Int,
    val wrongClicks: Int,
    val endReason: Stage1EndReason,
    val elapsedSeconds: Int
)

// the reasons for ending stage1
@Serializable
enum class Stage1EndReason { COMPLETED, TIME_UP }


// stage2 results
@Serializable
data class ItemResult(
    val id: String,
    val expected: String,
    val actual: String,
    val status: String   // CORRECT / MISSING / WRONG_QUANTITY / EXTRA
)

@Serializable
data class AssessmentResult(
    val timestamp: String,
    val items: List<ItemResult>,
    val totals: Map<String, Int>
)

// both stage1 + stage2 run
@Serializable
data class CombinedRunResult(
    val timestamp: String,
    val stage1: Stage1Result?,
    val stage2: AssessmentResult
)
