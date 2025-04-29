package org.example.hit.heal.hitber.core.utils

class BitmapToUploadUseCase {

    fun buildPath(
        clinicId: String,
        patientId: String,
        measurementId: String,
        pathDate: String,
        fileName: String = "image.png",
        testVersion: Int = 1
    ): String {
        val safeDate = pathDate.replace(":", "-").replace(".", "-")

        return "clinics/$clinicId/patients/$patientId/measurements/$measurementId/$safeDate/$testVersion/$fileName"
    }
}
