package org.example.hit.heal.cdt.data.network


import io.ktor.client.HttpClient
import io.ktor.client.plugins.onUpload
import io.ktor.client.request.forms.formData
import io.ktor.client.request.forms.submitFormWithBinaryData
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.serialization.SerializationException
import org.example.hit.heal.cdt.domain.CDTRepository
import org.example.hit.heal.cdt.utils.network.EmptyResult
import org.example.hit.heal.cdt.utils.network.NetworkError
import org.example.hit.heal.cdt.utils.network.Result

class CDTRepositoryImpl(
    private val httpClient: HttpClient
) : CDTRepository {

    private val BASE_URL = "https://rivka.hitheal.org.il"

    override suspend fun sendCDTRequest(body: CDTRequestBody): Result<String, NetworkError> {
        return try {
            val response = httpClient.post("$BASE_URL/send-cdt") {
                contentType(ContentType.Application.Json)
                setBody(body)
            }

            when (response.status.value) {
                in 200..299 -> {
                    val responseBody = response.bodyAsText()
                    println("CDT request success: ${response.status.value} - $responseBody")
                    Result.Success(responseBody)
                }

                401 -> Result.Error(NetworkError.UNAUTHORIZED)
                409 -> Result.Error(NetworkError.CONFLICT)
                408 -> Result.Error(NetworkError.REQUEST_TIMEOUT)
                413 -> Result.Error(NetworkError.PAYLOAD_TOO_LARGE)
                in 500..599 -> Result.Error(NetworkError.SERVER_ERROR)
                else -> Result.Error(NetworkError.UNKNOWN)
            }
        } catch (e: UnresolvedAddressException) {
            Result.Error(NetworkError.NO_INTERNET)
        } catch (e: SerializationException) {
            Result.Error(NetworkError.SERIALIZATION)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Error(NetworkError.UNKNOWN)
        }
    }

    override suspend fun uploadFile(
        request: FileUploadReqBody,
        progressListener: ((bytesSentTotal: Long, contentLength: Long?) -> Unit)?
    ): Result<String, NetworkError> {
        if (request.file.isEmpty()) return Result.Error(NetworkError.UNKNOWN)

        val response = try {
            httpClient.submitFormWithBinaryData(
                url = "${BASE_URL}/FileUpload/",
                formData = formData {
                    append("file", request.file, Headers.build {
                        append(HttpHeaders.ContentDisposition, "filename=${request.fileName}")
                        append(HttpHeaders.ContentType, ContentType.Image.PNG.toString())
                    })
                    append("file_name", request.fileName)
                    append("clinic_id", request.clinicId.toString())
                    append("user_id", request.userId.toString())
                    append("path", request.imageUrl)

                    // Serialize meta_data (CDTRequestBody) as JSON and send it
                    val json = kotlinx.serialization.json.Json.encodeToString(
                        CDTRequestBody.serializer(), request.metaData
                    )
                    append("meta_data", json, Headers.build {
                        append(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                    })
                }
            ) {
                progressListener?.let { onUpload(it) }
            }
        } catch (e: UnresolvedAddressException) {
            return Result.Error(NetworkError.NO_INTERNET)
        } catch (e: SerializationException) {
            return Result.Error(NetworkError.SERIALIZATION)
        } catch (e: Exception) {
            e.printStackTrace()
            return Result.Error(NetworkError.UNKNOWN)
        }

        return when (response.status.value) {
            in 200..299 -> {
                val responseBody = response.bodyAsText()
                println("Upload success: ${response.status.value} - $responseBody")
                Result.Success(responseBody)
            }

            401 -> Result.Error(NetworkError.UNAUTHORIZED)
            409 -> Result.Error(NetworkError.CONFLICT)
            408 -> Result.Error(NetworkError.REQUEST_TIMEOUT)
            413 -> Result.Error(NetworkError.PAYLOAD_TOO_LARGE)
            in 500..599 -> Result.Error(NetworkError.SERVER_ERROR)
            else -> Result.Error(NetworkError.UNKNOWN)
        }
    }


    override suspend fun uploadMeasureResponse(results: CDTRequestBody): EmptyResult<NetworkError> {
        val response = try {
            httpClient.post(urlString = "$BASE_URL/patientMeasureResponse/") {
                contentType(ContentType.Application.Json)
                setBody(results)
            }
        } catch (e: UnresolvedAddressException) {
            return Result.Error(NetworkError.NO_INTERNET)
        } catch (e: SerializationException) {
            return Result.Error(NetworkError.SERIALIZATION)
        }

        return when (response.status.value) {
            in 200..299 -> {
                Result.Success(Unit)
            }

            401 -> Result.Error(NetworkError.UNAUTHORIZED)
            409 -> Result.Error(NetworkError.CONFLICT)
            408 -> Result.Error(NetworkError.REQUEST_TIMEOUT)
            413 -> Result.Error(NetworkError.PAYLOAD_TOO_LARGE)
            in 500..599 -> Result.Error(NetworkError.SERVER_ERROR)
            else -> Result.Error(NetworkError.UNKNOWN)
        }
    }
}
