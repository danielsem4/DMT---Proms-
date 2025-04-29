package org.example.hit.heal.cdt.domain

import org.example.hit.heal.cdt.data.network.TransactionResult
import org.example.hit.heal.cdt.utils.network.Error

class UploadCDTResultsUseCase(
    private val repository: ClockRepository
) {
    suspend fun execute(): TransactionResult<String, Error> {
        return repository.sendCDTRequest()
    }
}
