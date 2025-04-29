package org.example.hit.heal.cdt.data.network


import androidx.compose.ui.graphics.ImageBitmap
import io.ktor.client.HttpClient
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.serialization.SerializationException
import org.example.hit.heal.cdt.domain.CDTRepository
import org.example.hit.heal.cdt.domain.TokenProvider
import org.example.hit.heal.cdt.utils.getCurrentFormattedDateTime
import org.example.hit.heal.cdt.utils.network.CDTError
import org.example.hit.heal.cdt.utils.network.EmptyResult
import org.example.hit.heal.cdt.utils.network.Error
import org.example.hit.heal.cdt.utils.network.NetworkError
import org.example.hit.heal.cdt.utils.network.Result
import org.example.hit.heal.cdt.utils.network.onError
import org.example.hit.heal.cdt.utils.network.onSuccess
import org.example.hit.heal.cdt.utils.toByteArray
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

class CDTRepositoryImpl(
    private val httpClient: HttpClient
) : CDTRepository {

    private val BASE_URL = "https://rivka.hitheal.org.il/api/v1"

    private var cdtResults: CDTResults = CDTResults()
    private var clockDrawing: ImageBitmap? = null
    private var timeSpentDrawing: Long = 0
    private var timeSpentSettingFirstClock: Long = 0
    private var timeSpentSettingSecondClock: Long = 0

    override suspend fun sendCDTRequest(): Result<String, Error> {
        val measurement = 21
        val patientId = 168
        val clinicId = 6
        val date = getCurrentFormattedDateTime()
        val version = 1
        val imgName = "clock_image.png"

        val imagePath = "clinics/$clinicId/patients/$patientId/measurements/$measurement/$date/$version/$imgName"

        // Step 1: Upload the clock image
        val uploadResult = uploadFileCog(imagePath)

        return uploadResult
            .onSuccess { uploadedImageUrl ->
                println("Image uploaded successfully: $uploadedImageUrl")

                // Step 2: Update CDT results with the uploaded image URL
                cdtResults.imageUrl = MeasureObjectString(
                    measureObject = 186,
                    value = uploadedImageUrl,
                    dateTime = getCurrentFormattedDateTime()
                )

                // Step 3: Build the full CDTRequestBody
                val body = CDTRequestBody(
                    measurement = measurement,
                    patientId = patientId,
                    date = date,
                    clinicId = clinicId,
                    test = cdtResults
                )

                println("Sending CDT request with body: $body")

                try {
                    val response = httpClient.post("$BASE_URL/patientMeasureResponse") {
                        setBody(body)
                    }

                    when (response.status.value) {
                        in 200..299 -> {
                            val responseBody = response.bodyAsText()
                            println("CDT request success: ${response.status.value} - $responseBody")
                            Result.Success(responseBody)
                        }
                        401 -> Result.Error(NetworkError.UNAUTHORIZED)
                        403 -> Result.Error(NetworkError.UNAUTHORIZED)
                        404 -> Result.Error(NetworkError.NOT_FOUND)
                        409 -> Result.Error(NetworkError.CONFLICT)
                        408 -> Result.Error(NetworkError.REQUEST_TIMEOUT)
                        413 -> Result.Error(NetworkError.PAYLOAD_TOO_LARGE)
                        in 500..599 -> Result.Error(NetworkError.SERVER_ERROR)
                        else -> Result.Error(NetworkError.UNKNOWN)
                    }
                } catch (e: UnresolvedAddressException) {
                    println("Failed to send CDT: no internet")
                    Result.Error(NetworkError.NO_INTERNET)
                } catch (e: SerializationException) {
                    println("Failed to send CDT: serialization error")
                    Result.Error(NetworkError.SERIALIZATION)
                } catch (e: Exception) {
                    println("Failed to send CDT: unknown error ${e.message}")
                    Result.Error(NetworkError.UNKNOWN)
                }
            }
            .onError { error ->
                println("Image upload failed, not sending CDT: $error")
                Result.Error(error) // Immediately stop if upload failed
            }
    }


    @OptIn(ExperimentalEncodingApi::class)
    override suspend fun uploadFileCog(imagePath: String): Result<String, Error> {
        val imageBytes = getClockDrawing()?.toByteArray() ?: byteArrayOf()
        if (imageBytes.isEmpty()) {
            println("Upload failed: No clock drawing available")
            return Result.Error(CDTError.EMPTY_FILE)
        }

        val token = TokenProvider.getCurrentToken()
        println("Uploading file with token: $token")

        val url = "${BASE_URL}/FileUpload/"
        println("Uploading to URL: $url")

        val base64EncodedFile = Base64.encode(imageBytes)

        val response = try {
            httpClient.post(url) {
                header("Authorization", "Token $token")
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
        } catch (e: UnresolvedAddressException) {
            println("Upload failed due to no internet: ${e.message}")
            return Result.Error(NetworkError.NO_INTERNET)
        } catch (e: SerializationException) {
            println("Upload failed due to serialization error: ${e.message}")
            e.printStackTrace()
            return Result.Error(NetworkError.SERIALIZATION)
        } catch (e: Exception) {
            println("Upload failed due to unknown error: ${e.message}")
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
            403 -> Result.Error(NetworkError.UNAUTHORIZED)
            404 -> Result.Error(NetworkError.NOT_FOUND)
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

    override fun getCDTResults(): CDTResults = cdtResults

    override fun updateCDTResults(results: CDTResults) {
        cdtResults = results
    }

    override fun getClockDrawing(): ImageBitmap? = clockDrawing

    override fun setClockDrawing(image: ImageBitmap?) {
        clockDrawing = image
    }

    override fun getTimeSpentDrawing(): Long = timeSpentDrawing

    override fun setTimeSpentDrawing(time: Long) {
        timeSpentDrawing = time
    }

    override fun getTimeSpentSettingFirstClock(): Long = timeSpentSettingFirstClock

    override fun setTimeSpentSettingFirstClock(time: Long) {
        timeSpentSettingFirstClock = time
    }

    override fun getTimeSpentSettingSecondClock(): Long = timeSpentSettingSecondClock

    override fun setTimeSpentSettingSecondClock(time: Long) {
        timeSpentSettingSecondClock = time
    }

    override fun setCDTResults(newRes: CDTResults) {
        this.cdtResults = newRes
    }
}
