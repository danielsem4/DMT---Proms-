package core.domain.use_case.cdt

import core.domain.api.AppApi

class UploadImageUseCase(private val api: AppApi) {
    suspend fun execute(imagePath: String, bytes: ByteArray) =
        api.uploadFileCog(imagePath, bytes)
}