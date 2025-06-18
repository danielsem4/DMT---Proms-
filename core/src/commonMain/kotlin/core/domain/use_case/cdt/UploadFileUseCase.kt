package core.domain.use_case.cdt

import core.domain.api.AppApi

class UploadFileUseCase(private val api: AppApi) {
    suspend fun execute(imagePath: String, bytes: ByteArray, clinicId: Int, userId: String) =
        api.uploadFileCog(imagePath, bytes, clinicId, userId)
}