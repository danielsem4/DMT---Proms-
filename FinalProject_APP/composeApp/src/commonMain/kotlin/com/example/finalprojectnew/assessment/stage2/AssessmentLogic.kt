package com.example.finalprojectnew.assessment.stage2

import com.example.finalprojectnew.assessment.AssessmentResult
import com.example.finalprojectnew.assessment.ItemResult
import kotlinx.datetime.Clock
import kotlinx.serialization.json.Json

object AssessmentLogic {

    /** מחזיר את הטיפוס המשותף com.example.finalprojectnew.assessment.AssessmentResult  */
    fun assess(actual: Map<String, Int>): AssessmentResult {
        val now = Clock.System.now().toString()
        val results = mutableListOf<ItemResult>()

        // כל המזהים שמעניינים אותנו
        val allIds = (GroundTruth.shoppingExpected.keys +
                GroundTruth.donationExpected.keys +
                actual.keys).distinct()

        for (id in allIds) {
            val actualQty = actual[id] ?: 0
            val expectedQty = GroundTruth.shoppingExpected[id]
                ?: GroundTruth.donationExpected[id]
                ?: 0

            val status = when {
                // ✅ כלל הבננות – בלי UNKNOWN:
                id == "banana" && actualQty == 0 -> "MISSING"
                id == "banana" && actualQty in 1..7 -> "CORRECT"
                id == "banana" && actualQty > 7 -> "WRONG_QUANTITY"

                // מוצר שלא אמור להיות: ציפייה = 0 וקיבלנו >0
                expectedQty == 0 && actualQty > 0 -> "EXTRA"

                // היה אמור להיות וקיבלנו 0
                expectedQty > 0 && actualQty == 0 -> "MISSING"

                // כמות שגויה
                expectedQty > 0 && actualQty != expectedQty -> "WRONG_QUANTITY"

                // תקין
                expectedQty > 0 && actualQty == expectedQty -> "CORRECT"

                else -> "WRONG_QUANTITY" // לא נגיע לכאן בפועל, רק הגנה
            }

            results += ItemResult(
                id = id,
                expected = if (id == "banana") "1-7" else expectedQty.toString(),
                actual = actualQty.toString(),
                status = status
            )
        }

        val totals: Map<String, Int> = results.groupingBy { it.status }.eachCount()
        return AssessmentResult(timestamp = now, items = results, totals = totals)
    }

    /** המרת תוצאה (הטיפוס המשותף) ל־JSON */
    fun toJson(result: AssessmentResult): String =
        Json { prettyPrint = true }
            .encodeToString(AssessmentResult.serializer(), result)
}
