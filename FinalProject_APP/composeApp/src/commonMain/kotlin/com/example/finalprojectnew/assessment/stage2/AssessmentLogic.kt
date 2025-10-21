package com.example.finalprojectnew.assessment.stage2

import com.example.finalprojectnew.assessment.AssessmentResult
import com.example.finalprojectnew.assessment.ItemResult
import kotlinx.datetime.Clock
import kotlinx.serialization.json.Json

object AssessmentLogic {

    fun assess(actual: Map<String, Int>): AssessmentResult {
        // assess= a function that gets the actual shopping cart status
        // AssessmentResult= and returns a summary result object
        val now = Clock.System.now().toString()
        val results = mutableListOf<ItemResult>() // List of results items

        // the correct items
                val allIds = (GroundTruth.shoppingExpected.keys +
                GroundTruth.donationExpected.keys +
                actual.keys).distinct()

        // calculate result for each product ID
        for (id in allIds) {
            val actualQty = actual[id] ?: 0 // how much the user actually added to the cart for the product (if none then 0)
            val expectedQty = GroundTruth.shoppingExpected[id] // expectedQty: The quantity we expected according to the truth lists (shopping/donations)
                ?: GroundTruth.donationExpected[id]
                ?: 0

            val status = when { // highlights for the Banana Rule
                id == "banana" && actualQty == 0 -> "MISSING"
                id == "banana" && actualQty in 1..7 -> "CORRECT"
                id == "banana" && actualQty > 7 -> "WRONG_QUANTITY"

                // Product that shouldn't exist: expectation = 0 and we got >0 -> EXTRA
                expectedQty == 0 && actualQty > 0 -> "EXTRA"

                // item that was supposed to be and didn't go into the cart -> MISSING
                expectedQty > 0 && actualQty == 0 -> "MISSING"

                // wrong quantity
                expectedQty > 0 && actualQty != expectedQty -> "WRONG_QUANTITY"

                // correct
                expectedQty > 0 && actualQty == expectedQty -> "CORRECT"

                else -> "WRONG_QUANTITY" // only for backup
            }

            results += ItemResult(
                id = id,
                expected = if (id == "banana") "1-7" else expectedQty.toString(),
                actual = actualQty.toString(),
                status = status
            )
        }
//totals: summary by status – how many CORRECT,MISSING, etc.
        val totals: Map<String, Int> = results.groupingBy { it.status }.eachCount()
        return AssessmentResult(timestamp = now, items = results, totals = totals)
    }

    // Convert result to JSON
    fun toJson(result: AssessmentResult): String =
        Json { prettyPrint = true } // pretty print
            .encodeToString(AssessmentResult.serializer(), result)
}
