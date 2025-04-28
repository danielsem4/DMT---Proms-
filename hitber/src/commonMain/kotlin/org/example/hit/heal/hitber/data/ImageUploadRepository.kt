package org.example.hit.heal.hitber.data

import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.HttpHeaders
import org.example.hit.heal.hitber.core.domain.Result
import org.example.hit.heal.hitber.core.domain.DataError
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.http.ContentDisposition
import io.ktor.http.Headers
import io.ktor.http.headers
import org.example.hit.heal.hitber.core.data.safeCall


class ImageUploadRepository(private val client: HttpClient) {

    suspend fun uploadFile(
        clinicId: Int,
        patientId: Int,
        measurement: Int,
        date: String,
        testVersion: String,
        file: String,
        fileName: String
    ): Result<Unit, DataError.Remote> {

        val url = "https://your-api-url/clinics/$clinicId/patients/$patientId/measurements/$measurement/${date.replace(":", "-")}/$testVersion/image.png"

        return safeCall {

            client.post(url) {
                headers {
                    append(HttpHeaders.ContentType, "multipart/form-data")
                }
                setBody(MultiPartFormDataContent(
                    formData {
                        append("file", file, Headers.build {
                            append(HttpHeaders.ContentDisposition, ContentDisposition.File.withParameter("filename", fileName).toString())
                        })
                        append("file_name", fileName)
                        append("clinic_id", clinicId.toString())
                        append("user_id", patientId.toString())
                        append("path", url)
                    }
                ))
            }
        }
    }
}