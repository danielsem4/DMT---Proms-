package org.example.hit.heal.hitber.data.network

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.http.*

class ImageUploadApi(
    private val client: HttpClient
) {
    suspend fun uploadImage(
        fileBytes: ByteArray,
        fileName: String,
        clinicId: Int,
        patientId: Int,
        path: String
    ): String {
        val response = client.post("FileUpload/") {
            setBody(
                MultiPartFormDataContent(
                    formData {
                        append("file", fileBytes, Headers.build {
                            append(HttpHeaders.ContentType, "image/png")
                            append(HttpHeaders.ContentDisposition, "filename=\"$fileName\"")
                        })
                        append("file_name", fileName)
                        append("clinic_id", clinicId.toString())
                        append("user_id", patientId.toString())
                        append("path", path)
                    }
                )
            )
        }
        return response.body()
    }
}