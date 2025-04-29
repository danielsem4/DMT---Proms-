package org.example.hit.heal.hitber.core.network

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.client.statement.bodyAsText
import io.ktor.http.*
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json
import org.example.hit.heal.hitber.core.data.safeCall
import org.example.hit.heal.hitber.core.domain.DataError
import org.example.hit.heal.hitber.core.domain.Result
import org.example.hit.heal.hitber.di.AppJson

private const val BASE_URL = "https://generic2.hitheal.org.il/api/v1/"

class KtorAppRemoteDataSource(
    private val httpClient: HttpClient
) : AppApi {

    override suspend fun uploadImage(
        base64Image: String,
        fileName: String,
        clinicId: Int,
        userId: Int,
        imagePath: String
    ): Result<Unit, DataError.Remote> = safeCall<Unit> {
        println("🔄 Starting image upload...")
        println("📄 fileName: $fileName")
        println("🏥 clinicId: $clinicId")
        println("👤 userId: $userId")
        println("📁 path: $imagePath")
        println("🖼️ base64Image (first 100 chars): ${base64Image.take(100)}...")

        val formData = formData {
            append("file", base64Image, Headers.build {
                append(HttpHeaders.ContentType, ContentType.Text.Plain)
            })
            append("file_name", fileName)
            append("clinic_id", clinicId.toString())
            append("user_id", userId.toString())
            append("path", imagePath)
        }

        val response = httpClient.post("${BASE_URL}FileUpload/") {
            setBody(MultiPartFormDataContent(formData))
        }

        println("✅ Response status: ${response.status}")
        println("📥 Response body: ${response.bodyAsText()}")

        response
    }



    override suspend fun <T> uploadEvaluationTest(
        results: T,
        serializer: KSerializer<T>
    ): Result<Unit, DataError.Remote> = safeCall<Unit> {
        println("🔄 Starting uploadEvaluationTest...")

            val jsonString = AppJson.encodeToString(serializer, results)
            println("📦 Encoded JSON string:\n${jsonString}")

        val response = httpClient.post("${BASE_URL}patientMeasureResponse/") {
            println("🌐 Sending POST request to: ${BASE_URL}patientMeasureResponse/")
            contentType(ContentType.Application.Json)
            setBody(jsonString)
        }

        println("✅ HTTP Response status: ${response.status}")
        println("📥 HTTP Response body: ${response.bodyAsText()}")

        response
    }

//        httpClient.post("$BASE_URL/patientMeasureResponse/") {
//            contentType(ContentType.Application.Json)
//            setBody(jsonString)
//        }
//    }

}