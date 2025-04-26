package org.example.hit.heal.hitber.data

import org.example.hit.heal.hitber.data.network.ImageUploadApi

class ImageUploadRepository(
    private val api: ImageUploadApi
) {
    suspend fun uploadImage(
        fileBytes: ByteArray,
        fileName: String,
        clinicId: Int,
        patientId: Int,
        measurement: String,
        date: String,
        testVersion: String
    ): String {
        val path = buildPath(clinicId, patientId, measurement, date, testVersion)
        return api.uploadImage(fileBytes, fileName, clinicId, patientId, path)
    }

    private fun buildPath(
        clinicId: Int,
        patientId: Int,
        measurement: String,
        date: String,
        testVersion: String
    ): String {
        return "clinics/$clinicId/patients/$patientId/measurements/$measurement/$date/$testVersion/image.png"
    }
}