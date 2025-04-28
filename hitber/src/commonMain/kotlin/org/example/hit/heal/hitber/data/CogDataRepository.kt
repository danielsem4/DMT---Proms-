package org.example.hit.heal.hitber.data

import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.contentType
import org.example.hit.heal.hitber.core.data.safeCall
import org.example.hit.heal.hitber.core.domain.DataError
import org.example.hit.heal.hitber.data.model.CogData
import org.example.hit.heal.hitber.core.domain.Result

class CogDataRepository(private val client: HttpClient) {

    suspend fun uploadMeasureResponse(cogData: CogData): Result<Unit, DataError.Remote> {
        return safeCall {
            client.post("https://your-api-url/patientMeasureResponse/") {
                contentType(io.ktor.http.ContentType.Application.Json)
                setBody(cogData)
            }
        }
    }
}
