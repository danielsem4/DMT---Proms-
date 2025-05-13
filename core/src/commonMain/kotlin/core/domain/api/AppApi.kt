package core.domain.api

import core.data.model.SuccessfulLoginResponse
import core.domain.DataError
import core.domain.EmptyResult
import core.domain.Result
import kotlinx.serialization.KSerializer


interface AppApi {
    suspend fun login(
        email: String,
        password: String
    ): Result<SuccessfulLoginResponse, DataError.Remote>

    suspend fun <T> sendResults(
        results: T,
        serializer: KSerializer<T>
    ): Result<String, DataError.Remote>

    suspend fun uploadFileCog(
        imagePath: String,
        imageBytes: ByteArray,
        clinicId: Int,
        userId: Int
    ): EmptyResult<DataError.Remote>
}