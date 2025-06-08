package core.domain.use_case.cdt

import core.domain.DataError
import core.domain.Result
import core.domain.api.AppApi
import kotlinx.serialization.KSerializer

class UploadTestResultsUseCase(
    private val api: AppApi
) {
    suspend fun <T> execute(results: T, serializer: KSerializer<T>): Result<String, DataError> =
        api.sendResults(results, serializer)
}