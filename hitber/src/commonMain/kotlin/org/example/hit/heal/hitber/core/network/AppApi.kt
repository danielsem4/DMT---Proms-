package org.example.hit.heal.hitber.core.network

import kotlinx.serialization.KSerializer
import org.example.hit.heal.hitber.core.domain.DataError
import org.example.hit.heal.hitber.core.domain.Result

interface AppApi {
    suspend fun uploadImage(
        base64Image: String,
        fileName: String,
        clinicId: Int,
        userId: Int,
        imagePath: String
    ): Result<Unit, DataError.Remote>

    suspend fun <T> uploadEvaluationTest(
        results: T,
        serializer: KSerializer<T>
    ): Result<Unit, DataError.Remote>
}
