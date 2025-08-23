package core.data.remote.impl

import core.data.model.ActivityItem
import core.data.model.ActivityItemReport
import core.data.model.LoginRequest
import core.data.model.Medications.Medication
import core.data.model.Medications.MedicationReport
import core.data.model.ModulesResponse
import core.data.model.SuccessfulLoginResponse
import core.data.model.evaluation.Evaluation
import core.data.storage.Storage
import core.domain.DataError
import core.domain.Result
import core.domain.api.AppApi
import core.domain.map
import core.network.AppConfig.BASE_URL
import core.network.AppConfig.BASE_URL_DEV
import core.network.getWithAuth
import core.network.postWithAuth
import core.network.safeCall
import io.ktor.client.HttpClient
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
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

/**
 * Remote data source implementation using Ktor HttpClient.
 * Handles all network calls defined in the AppApi interface.
 */

class KtorAppRemoteDataSource(
    private val httpClient: HttpClient,
    val storage: Storage
) : AppApi {

    // Base URL for all endpoints
    private val baseUrl = BASE_URL

    override suspend fun login(email: String, password: String):
            Result<SuccessfulLoginResponse, DataError.Remote> = safeCall {
        httpClient.post("${baseUrl}login/") {
            contentType(ContentType.Application.Json)
            setBody(LoginRequest(email, password))
        }
    }

    override suspend fun <T> sendResults(
        results: T,
        serializer: KSerializer<T>
    ): Result<String, DataError.Remote> {
        val json = Json {
            ignoreUnknownKeys = true
            isLenient = true
            prettyPrint = true
            encodeDefaults = true
        }
        val url = "${baseUrl}patientMeasureResponse/"
        val body = json.encodeToString(serializer, results)
        println("the body is: $body")

        return httpClient.postWithAuth<String>(
            url = url,
            storage = storage
        ) {
            setBody(body)
        }
    }

    @OptIn(ExperimentalEncodingApi::class)
    override suspend fun uploadFileCog(
        imagePath: String,
        imageBytes: ByteArray,
        clinicId: Int,
        userId: String
    ): Result<Int, DataError.Remote> {
        val url = "${baseUrl}FileUpload/"
        val base64EncodedFile: String = Base64.encode(imageBytes)

        return httpClient.postWithAuth<Int>(
            url = url,
            storage = storage
        ) {
            setBody(
                MultiPartFormDataContent(
                    formData {
                        append("file", base64EncodedFile, Headers.build {
                            append(HttpHeaders.ContentDisposition, "form-data; name=\"file\"")
                            append(HttpHeaders.ContentType, "text/plain")
                        })
                        append("file_name", imagePath)
                        append("clinic_id", clinicId)
                        append("user_id", userId)
                        append("path", imagePath)
                    }
                )
            )
        }
    }

    override suspend fun getModules(clinicId: Int): Result<ArrayList<ModulesResponse>, DataError.Remote> =
        httpClient
            .getWithAuth<List<ModulesResponse>>(
                "${baseUrl}getModules/",
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
            url = "${baseUrl}Medication_list/",
            storage = storage
        ) {
            parameter("clinic_id",  clinicId)
            parameter("patient_id", patientId)
        }

    override suspend fun reportMedicationTook(
        body: MedicationReport
    ): Result<Unit, DataError.Remote> =
        httpClient.postWithAuth<Unit>(
            url = "${baseUrl}report_medication/",
            storage  = storage
        ) {
            setBody(body)
        }


    override suspend fun setMedicationNotifications(
        results: Medication
    ): Result<Unit, DataError.Remote> =
        httpClient.postWithAuth<Unit>(
            url = "${baseUrl}patientNotificationData/",
            storage = storage
        ) {
            setBody(results)
        }

    override suspend fun getPatientMeasureReport(
        clinicId: Int,
        patientId: Int
    ): Result<List<Evaluation>, DataError.Remote> =
        httpClient.getWithAuth<List<Evaluation>>(
            url = "${baseUrl}PatientMeasurements/",
            storage = storage
        ) {
            parameter("clinic_id", clinicId)
            parameter("patient_id", patientId)
        }

    override suspend fun getSpecificEvaluation(
        clinicId: Int,
        patientId: Int,
        evaluationName: String
    ): Result<Evaluation, DataError.Remote> =
        httpClient.getWithAuth<Evaluation>(
            url = "${baseUrl}getOneEvaluation/",
            storage = storage
        ) {
            parameter("clinic_id",  clinicId)
            parameter("patient_id", patientId)
            parameter("Evaluation_name", evaluationName)
        }

    override suspend fun reportActivity(
        body: ActivityItemReport
    ): Result<Unit, DataError.Remote> =
        httpClient.postWithAuth<Unit>(
            url = "${baseUrl}Activity_report/",
            storage = storage
        ) {
            contentType(ContentType.Application.Json)
            setBody(body)
        }

    override suspend fun getPatientActivities(
        clinicId: Int
    ): Result<List<ActivityItem>, DataError.Remote> =
        httpClient.getWithAuth<List<ActivityItem>>(
            url = "${baseUrl}Activities_list/",
            storage = storage
        ) {
            parameter("clinic_id", clinicId)
        }


}