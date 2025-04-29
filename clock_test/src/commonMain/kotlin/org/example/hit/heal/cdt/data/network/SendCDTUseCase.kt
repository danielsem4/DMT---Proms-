package org.example.hit.heal.cdt.data.network

import org.example.hit.heal.cdt.domain.ClockRepository

class SendCDTUseCase(
    private val repo: ClockRepository
) {
    suspend operator fun invoke() = repo.sendCDTRequest()
}
