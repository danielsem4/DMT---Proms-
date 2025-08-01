package core.domain.use_case

class BitmapToUploadUseCase {

    fun buildPath(
        clinicId: Int,
        patientId: String,
        measurementId: Int,
        pathDate: String,
        fileName: String = "image.png",
        testVersion: Int = 1
    ): String {

        return "clinics/$clinicId/patients/$patientId/measurements/$measurementId/$pathDate/$testVersion/$fileName"
    }
}
