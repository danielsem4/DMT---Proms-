package org.example.hit.heal.cdt.data.network

import core.domain.DataError
import core.domain.session.TokenProvider
import core.network.safeCall
import core.utils.getCurrentFormattedDateTime
import io.ktor.client.HttpClient
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import org.example.hit.heal.cdt.domain.ClockRepository
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi
import core.domain.Result as Result1


class ClockRepositoryImpl(
    private val httpClient: HttpClient
) : ClockRepository {

    private val BASE_URL = "https://rivka.hitheal.org.il/api/v1"

    override suspend fun sendCDTRequest(
        cdtResults: CDTResults
    ): Result1<String, DataError.Remote> {
        val measurement = 21
        val patientId = 168
        val clinicId = 6
        val date = getCurrentFormattedDateTime()

        val body = CDTRequestBody(
            measurement = measurement,
            patientId = patientId,
            date = date,
            clinicId = clinicId,
            test = cdtResults
        )

        println("Sending CDT Results: $body")

        val url = "$BASE_URL/patientMeasureResponse/"

        return safeCall {
            httpClient.post(url) {
                setBody(body)
                header("Authorization", "Token ${TokenProvider.getCurrentToken()}")
            }
        }
    }


    @OptIn(ExperimentalEncodingApi::class)
    override suspend fun uploadFileCog(
        imagePath: String,
        imageBytes: ByteArray
    ): Result1<String, DataError.Remote> {
        val token = TokenProvider.getCurrentToken()
        println("Uploading file with token: $token")

        val url = "${BASE_URL}/FileUpload/"
        println("Uploading to URL: $url")

        val base64EncodedFile = Base64.encode(imageBytes)

        return safeCall {
            httpClient.post(url) {
                setBody(
                    MultiPartFormDataContent(
                        formData {
                            append("file", base64EncodedFile, Headers.build {
                                append(HttpHeaders.ContentDisposition, "form-data; name=\"file\"")
                                append(HttpHeaders.ContentType, "text/plain")
                            })
                            append("file_name", imagePath)
                            append("clinic_id", "6") // fixed
                            append("user_id", "168") // fixed
                            append("path", imagePath)
                        }
                    )
                )
            }
        }
    }
}
