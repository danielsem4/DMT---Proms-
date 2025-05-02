package org.example.hit.heal.hitber.core.utils

import org.example.hit.heal.hitber.core.domain.DataError
import org.example.hit.heal.hitber.core.domain.Result
import org.example.hit.heal.hitber.core.network.AppApi
import kotlinx.serialization.KSerializer

class UploadEvaluationUseCase(
    private val api: AppApi
) {
    suspend fun <T> execute(
        evaluationResults: T,
        serializer: KSerializer<T>
    ): Result<Unit, DataError.Remote> {
        println("Uploading evaluation: $evaluationResults")
        val result = api.uploadEvaluationTest(evaluationResults, serializer)
        println("Uploading evaluation: $result")
        return  result
    }
}
