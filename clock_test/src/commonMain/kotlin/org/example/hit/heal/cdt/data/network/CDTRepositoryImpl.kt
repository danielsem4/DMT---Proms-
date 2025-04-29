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
import org.example.hit.heal.cdt.presentation.components.ClockTime
import org.example.hit.heal.cdt.utils.getCurrentFormattedDateTime
import org.example.hit.heal.cdt.utils.network.CDTError
import org.example.hit.heal.cdt.utils.network.Error
import org.example.hit.heal.cdt.utils.network.NetworkError
import org.example.hit.heal.cdt.utils.network.Result
import org.example.hit.heal.cdt.utils.toByteArray
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

class CDTRepositoryImpl(
    private val httpClient: HttpClient
) : CDTRepository {

    private val BASE_URL = "https://rivka.hitheal.org.il/api/v1"

    private var cdtResults = CDTResults()
    private var clockDrawing: ImageBitmap? = null
    private var timeSpentDrawing: Long = 0
    private var timeSpentSettingFirstClock: Long = 0
    private var timeSpentSettingSecondClock: Long = 0

    override suspend fun sendCDTRequest(): TransactionResult<String, Error> {
        val measurement = 21
        val patientId = 168
        val clinicId = 6
        val date = getCurrentFormattedDateTime()
        val version = 1
        val imgName = "clock_image.png"

        val imagePath = "clinics/$clinicId/patients/$patientId/measurements/$measurement/$date/$version/$imgName"

        val uploadResult = uploadFileCog(imagePath)

        return when (uploadResult) {
            is Result.Success -> {
                val uploadedImageUrl = uploadResult.data
                println("Image uploaded successfully: $uploadedImageUrl")

                cdtResults.imageUrl = MeasureObjectString(
                    measureObject = 186,
                    value = uploadedImageUrl,
                    dateTime = getCurrentFormattedDateTime()
                )

                val body = CDTRequestBody(
                    measurement = measurement,
                    patientId = patientId,
                    date = date,
                    clinicId = clinicId,
                    test = cdtResults
                )

                val url = "$BASE_URL/patientMeasureResponse/"
                try {
                    val response = httpClient.post(url) {
                        setBody(body)
                    }

                    when (response.status.value) {
                        in 200..299 -> {
                            val responseBody = response.bodyAsText()
                            println("CDT request success: ${response.status.value} - $responseBody")
                            TransactionResult.Success(responseBody)
                        }
                        401 -> TransactionResult.SendFailure(NetworkError.UNAUTHORIZED)
                        403 -> TransactionResult.SendFailure(NetworkError.UNAUTHORIZED)
                        404 -> TransactionResult.SendFailure(NetworkError.NOT_FOUND)
                        409 -> TransactionResult.SendFailure(NetworkError.CONFLICT)
                        408 -> TransactionResult.SendFailure(NetworkError.REQUEST_TIMEOUT)
                        413 -> TransactionResult.SendFailure(NetworkError.PAYLOAD_TOO_LARGE)
                        in 500..599 -> TransactionResult.SendFailure(NetworkError.SERVER_ERROR)
                        else -> TransactionResult.SendFailure(NetworkError.UNKNOWN)
                    }
                } catch (e: UnresolvedAddressException) {
                    TransactionResult.SendFailure(NetworkError.NO_INTERNET)
                } catch (e: SerializationException) {
                    TransactionResult.SendFailure(NetworkError.SERIALIZATION)
                } catch (e: Exception) {
                    TransactionResult.SendFailure(NetworkError.UNKNOWN)
                }
            }
            is Result.Error -> {
                println("Image upload failed: ${uploadResult.error}")
                TransactionResult.UploadFailure(uploadResult.error)
            }
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

    override fun updateFirstClockTime(newTime: ClockTime) {
        val formattedDateTime = getCurrentFormattedDateTime()
        with(cdtResults){
            timeChange1.value = newTime.toString()
            timeChange1.dateTime = formattedDateTime

            hourChange1.value = newTime.hours
            hourChange1.dateTime = formattedDateTime

            minuteChange1.value = newTime.minutes
            minuteChange1.dateTime = formattedDateTime
        }
    }

    override fun updateSecondClockTime(newTime: ClockTime) {
        val formattedDateTime = getCurrentFormattedDateTime()
        with(cdtResults){
            timeChange2.value = newTime.toString()
            timeChange2.dateTime = formattedDateTime

            hourChange2.value = newTime.hours
            hourChange2.dateTime = formattedDateTime

           minuteChangeUrl2.value = newTime.minutes
            minuteChangeUrl2.dateTime = formattedDateTime
        }
    }
}
