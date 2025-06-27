package core.data.remote.impl

import core.data.model.LoginRequest
import core.data.model.ModulesResponse
import core.data.model.SuccessfulLoginResponse
import core.data.storage.Storage
import core.domain.DataError
import core.domain.EmptyResult
import core.domain.Result
import core.domain.api.AppApi
import core.network.AppConfig.BASE_URL
import core.network.safeCall
import core.util.PrefKeys
import io.ktor.client.HttpClient
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi


class GenericApiImpl() {

}

class KtorAppRemoteDataSource(
    private val httpClient: HttpClient,
    val storage: Storage
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
        val url = "$BASE_URL/patientMeasureResponse/"

        val body = Json.encodeToString(serializer, results)

        return safeCall {
            httpClient.post(url) {
                contentType(ContentType.Application.Json)
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
    ): EmptyResult<DataError.Remote> {
        val url = "$BASE_URL/FileUpload/"
        println("Uploading to URL: $url")

        val base64EncodedFile: String = Base64.encode(imageBytes)

        return safeCall {
            httpClient.post(url) {
                val token: String = storage.get(PrefKeys.token)!!

                header(HttpHeaders.Authorization, "Token $token")

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
    }

    override suspend fun getModules(clinicId: Int):
            Result<List<ModulesResponse>, DataError.Remote> = safeCall {
        httpClient.get("${BASE_URL}getModules?clinic_id=$clinicId") {
            val token: String = storage.get(PrefKeys.token)!!

            header(HttpHeaders.Authorization, "Token $token")
            contentType(ContentType.Application.Json)
        }
    }

}