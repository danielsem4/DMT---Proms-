package com.example.finalprojectnew.stage2.assessment

import com.example.finalprojectnew.assessment.storage.AssessmentStorage
import com.example.finalprojectnew.assessment.stage2.AssessmentLogic   // ✅ זה ה-IMPORT הנכון
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object FileCartAssessmentRepository {

    private const val DIR_NAME = "stage2answers"

    suspend fun assessAndSave(cart: Map<String, Int>) = withContext(Dispatchers.Default) {
        val result = AssessmentLogic.assess(cart)
        val json = AssessmentLogic.toJson(result)
        val filename = "stage2_${System.currentTimeMillis()}.json"
        AssessmentStorage.writeJson(DIR_NAME, filename, json)
    }
}
