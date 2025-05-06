package org.example.hit.heal.cdt.domain

import core.domain.DataError
import core.domain.Result
import org.example.hit.heal.cdt.data.network.CDTResults

class UploadCDTResultsUseCase(
    private val api: ClockRepository
) {
    suspend fun execute(results: CDTResults): Result<String, DataError> = api.sendCDTRequest(results)
}
