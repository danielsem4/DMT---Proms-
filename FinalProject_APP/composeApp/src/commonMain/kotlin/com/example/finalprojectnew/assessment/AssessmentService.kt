package com.example.finalprojectnew.assessment
// collects Stage 1 & Stage 2 results, and make them JSON

import com.example.finalprojectnew.assessment.stage2.AssessmentLogic
import com.example.finalprojectnew.assessment.storage.AssessmentStorage
import kotlinx.datetime.Clock // the time of the JSON
import kotlinx.serialization.json.Json

object AssessmentService {

    private const val DIR_NAME = "results" //the name of the file

    private val json = Json { prettyPrint = true } // set the file to be organized and readable

// in the end of stage1
        fun recordStage1(result: Stage1Result) {
        SessionRecorder.recordStage1(result)
    }

    // at the end of stage2 - calculates the result of step2
    // combines with step1 and saves to a single JSON file
    // returns the full path to the file
    suspend fun saveStage2AndFlush(cart: Map<String, Int>): String {
        val stage2 = AssessmentLogic.assess(cart)

        val combined = CombinedRunResult(
            timestamp = Clock.System.now().toString(),
            stage1 = SessionRecorder.stage1,
            stage2 = stage2
        )

        val payload = json.encodeToString(CombinedRunResult.serializer(), combined)

// make unique file name by the time
        val epochMs = Clock.System.now().toEpochMilliseconds()
        val filename = "run_${epochMs}.json"

        // save the file
        val path = AssessmentStorage.writeJson(DIR_NAME, filename, payload)

        SessionRecorder.clearStage1()
        return path
    }
}
