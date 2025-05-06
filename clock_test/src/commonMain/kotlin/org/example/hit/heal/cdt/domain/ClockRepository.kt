package org.example.hit.heal.cdt.domain

import core.domain.DataError.Remote
import core.domain.Result
import org.example.hit.heal.cdt.data.network.CDTResults

interface ClockRepository {
    suspend fun uploadFileCog(imagePath: String, imageBytes: ByteArray):Result<String, Remote>
    suspend fun sendCDTRequest(cdtResults: CDTResults): Result<String, Remote>
}