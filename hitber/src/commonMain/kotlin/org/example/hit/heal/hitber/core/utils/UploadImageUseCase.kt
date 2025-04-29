package org.example.hit.heal.hitber.core.utils

import androidx.compose.ui.autofill.ContentType
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.utils.io.core.*
import io.ktor.client.statement.*
import io.ktor.client.*
import io.ktor.client.engine.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.client.call.*
import io.ktor.client.request.forms.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.plugins.auth.*
import io.ktor.client.plugins.auth.providers.*
import io.ktor.client.plugins.cookies.*

import androidx.compose.ui.graphics.ImageBitmap
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.coroutines.*
import org.example.hit.heal.hitber.utils.toBase64
import io.ktor.client.statement.*
import io.ktor.util.encodeBase64
import org.example.hit.heal.hitber.core.domain.DataError
import org.example.hit.heal.hitber.utils.toByteArray
import org.example.hit.heal.hitber.core.domain.Result
import org.example.hit.heal.hitber.core.network.AppApi

class UploadImageUseCase(
    private val api: AppApi
) {
    suspend fun execute(
        bitmap: ImageBitmap,
        clinicId: Int,
        userId: Int,
        path: String
    ): Result<Unit, DataError.Remote> {
        val base64Image = bitmap.toByteArray().toBase64()
        val fileName = "image.png"

        return api.uploadImage(
            base64Image = base64Image,
            fileName = fileName,
            clinicId = clinicId,
            userId = userId,
            imagePath = path
        )
    }
}