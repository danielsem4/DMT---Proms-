package org.example.hit.heal.cdt.data.network

import org.example.hit.heal.cdt.domain.CDTRepository

class SendCDTUseCase(
    private val repo: CDTRepository
) {
    suspend operator fun invoke() = repo.sendCDTRequest()
}
