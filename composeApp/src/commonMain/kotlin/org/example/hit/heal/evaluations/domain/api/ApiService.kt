package org.example.hit.heal.evaluations.domain.api

object ApiService {
    suspend fun uploadDrawingImage(bytes: ByteArray): String = platformUploadDrawingImage(bytes)
}

expect suspend fun platformUploadDrawingImage(bytes: ByteArray): String
