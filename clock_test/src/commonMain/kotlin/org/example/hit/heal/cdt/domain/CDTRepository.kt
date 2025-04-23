package org.example.hit.heal.cdt.domain

import org.example.hit.heal.cdt.data.network.CDTRequestBody
import org.example.hit.heal.cdt.data.network.FileUploadReqBody
import org.example.hit.heal.cdt.utils.network.EmptyResult
import org.example.hit.heal.cdt.utils.network.NetworkError
import org.example.hit.heal.cdt.utils.network.Result

interface CDTRepository {
    suspend fun sendCDTRequest(body: CDTRequestBody): Result<String, NetworkError>
    suspend fun uploadMeasureResponse(results: CDTRequestBody): EmptyResult<NetworkError>
    suspend fun uploadFile(
        request: FileUploadReqBody,
        progressListener: ((bytesSentTotal: Long, contentLength: Long?) -> Unit)? = null
    ): Result<String, NetworkError>
}