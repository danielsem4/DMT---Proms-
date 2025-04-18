package org.example.hit.heal.evaluations.domain.api

import io.ktor.client.call.body
import io.ktor.client.request.forms.formData
import io.ktor.client.request.forms.submitFormWithBinaryData
import io.ktor.client.statement.HttpResponse
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders

actual suspend fun platformUploadDrawingImage(bytes: ByteArray): String {
    val response: HttpResponse = ApiClient.client.submitFormWithBinaryData(
        url = "https://your-api.com/upload", // replace with real
        formData = formData {
            append(
                "file",
                bytes,
                Headers.build {
                    append(HttpHeaders.ContentType, "image/png")
                    append(HttpHeaders.ContentDisposition, "filename=drawing.png")
                }
            )
        }
    )
    // Example parsing JSON response { "imageUrl": "..." }
    val body = response.body<String>() // or use response.body<UploadResponse>() with serialization
    return body // or parse it properly
}
