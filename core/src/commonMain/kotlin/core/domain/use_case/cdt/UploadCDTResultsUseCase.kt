package core.domain.use_case.cdt

import core.data.model.cdt.CDTRequestBody
import core.domain.DataError
import core.domain.Result
import core.domain.api.AppApi

class UploadCDTResultsUseCase(
    private val api: AppApi
) {
    suspend fun execute(results: CDTRequestBody): Result<String, DataError> =
        api.sendResults(results,CDTRequestBody.serializer())
}