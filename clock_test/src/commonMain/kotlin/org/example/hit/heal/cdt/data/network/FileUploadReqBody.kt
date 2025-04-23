package org.example.hit.heal.cdt.data.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FileUploadReqBody(
    @SerialName("file") val file: ByteArray,
    @SerialName("file_name") val fileName: String,
    @SerialName("clinic_id") val clinicId: Int,
    @SerialName("user_id") val userId: Int,
    @SerialName("path") val imageUrl: String,
    @SerialName("meta_data") val metaData: CDTRequestBody
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as FileUploadReqBody

        if (clinicId != other.clinicId) return false
        if (userId != other.userId) return false
        if (!file.contentEquals(other.file)) return false
        if (fileName != other.fileName) return false
        if (imageUrl != other.imageUrl) return false
        if (metaData != other.metaData) return false

        return true
    }

    override fun hashCode(): Int {
        var result = clinicId
        result = 31 * result + userId
        result = 31 * result + file.contentHashCode()
        result = 31 * result + fileName.hashCode()
        result = 31 * result + imageUrl.hashCode()
        result = 31 * result + metaData.hashCode()
        return result
    }
}
