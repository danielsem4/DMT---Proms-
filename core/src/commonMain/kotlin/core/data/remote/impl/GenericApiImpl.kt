package core.data.remote.impl

import core.data.model.LoginRequest
import core.data.model.Medications.Medication
import core.data.model.Medications.MedicationReport
import core.data.model.ModulesResponse
import core.data.model.SuccessfulLoginResponse
import core.data.storage.Storage
import core.domain.DataError
import core.domain.EmptyResult
import core.domain.Result
import core.domain.api.AppApi
import core.domain.map
import core.network.AppConfig.BASE_URL
import core.network.getWithAuth
import core.network.postWithAuth
import core.network.safeCall
import core.util.PrefKeys
import io.ktor.client.HttpClient
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.http.cio.Request
import io.ktor.http.contentType
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi


class GenericApiImpl

class KtorAppRemoteDataSource(
    private val httpClient: HttpClient,
    private val storage: Storage
) : AppApi {

    override suspend fun login(email: String, password: String):
            Result<SuccessfulLoginResponse, DataError.Remote> = safeCall {
        httpClient.post("${BASE_URL}login/") {
            contentType(ContentType.Application.Json)
            setBody(LoginRequest(email, password))
        }
    }

    override suspend fun <T> sendResults(
        results: T,
        serializer: KSerializer<T>
    ): Result<String, DataError.Remote> {
        val url = "${BASE_URL}patientMeasureResponse/"

        val body = Json.encodeToString(serializer, results)

        return safeCall {
            httpClient.post(url) {
                contentType(ContentType.Application.Json)

                val token: String = storage.get(PrefKeys.token)!!
                header(HttpHeaders.Authorization, "Token $token")

                setBody(body)
            }
        }
    }

    @OptIn(ExperimentalEncodingApi::class)
    override suspend fun uploadFileCog(
        imagePath: String,
        imageBytes: ByteArray,
        clinicId: Int,
        userId: String
    ): EmptyResult<DataError.Remote> { // This translates to Result<Unit, DataError.Remote> for safeCall
        val url = "${BASE_URL}FileUpload/"
        val base64EncodedFile: String = Base64.encode(imageBytes)

        return safeCall { // T will be Unit here due to the return type of EmptyResult
            httpClient.post(url) {
                val token: String = storage.get(PrefKeys.token)
                    ?: throw IllegalStateException("Auth token not found")

                header(HttpHeaders.Authorization, "Token $token")

                setBody(
                    MultiPartFormDataContent(
                        formData {
                            append("file", base64EncodedFile, Headers.build {
                                append(HttpHeaders.ContentDisposition, "form-data; name=\"file\"")
                                append(
                                    HttpHeaders.ContentType,
                                    "text/plain"
                                ) // Content-Type for this specific part
                            })
                            append("file_name", imagePath)
                            append("clinic_id", clinicId)
                            append("user_id", userId)
                            append("path", imagePath)
                        }
                    )
                )
                // Ktor handles the main "Content-Type: multipart/form-data" header automatically
            }
        }
    }

    override suspend fun getModules(clinicId: Int): Result<ArrayList<ModulesResponse>, DataError.Remote> =
        httpClient
            .getWithAuth<List<ModulesResponse>>(
                "${BASE_URL}getModules",
                storage
            ) {
                parameter("clinic_id", clinicId)
            }
            .map { ArrayList(it) }


    // Medications API Implementation
    override suspend fun getAllPatientMedicines(
        clinicId: Int,
        patientId: Int
    ): Result<List<Medication>, DataError.Remote> =
        httpClient.getWithAuth<List<Medication>>(
            url      = "${BASE_URL}Medication_list/",
            storage  = storage
        ) {
            parameter("clinic_id",  clinicId)
            parameter("patient_id", patientId)
        }

    override suspend fun reportMedicationTook(
        body: MedicationReport
    ): Result<Unit, DataError.Remote> =
        httpClient.postWithAuth<Unit>(
            url      = "${BASE_URL}report_medication/",
            storage  = storage
        ) {
            setBody(body)
        }


    override suspend fun setMedicationNotifications(
        results: Request
    ): Result<Unit, DataError.Remote> =
        httpClient.postWithAuth<Unit>(
            url      = "${BASE_URL}patientNotificationData/",
            storage  = storage
        ) {
            setBody(results)
        }

}