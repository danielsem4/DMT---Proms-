package org.example.hit.heal.cdt.domain

class UploadImageUseCase(private val api: ClockRepository) {
    suspend fun execute(imagePath: String, bytes: ByteArray) =
        api.uploadFileCog(imagePath, bytes)
}
