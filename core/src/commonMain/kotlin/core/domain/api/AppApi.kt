package core.domain.api

import core.data.model.Medications.Medication
import core.data.model.Medications.MedicationReport
import core.data.model.ModulesResponse
import core.data.model.SuccessfulLoginResponse
import core.domain.DataError
import core.domain.EmptyResult
import core.domain.Result
import io.ktor.http.cio.Request
import kotlinx.serialization.KSerializer


interface AppApi {
    suspend fun login(
        email: String,
        password: String
    ): Result<SuccessfulLoginResponse, DataError.Remote>

    suspend fun <T> sendResults(
        results: T,
        serializer: KSerializer<T>
    ): Result<String, DataError.Remote>

    suspend fun uploadFileCog(
        imagePath: String,
        imageBytes: ByteArray,
        clinicId: Int,
        userId: String
    ): EmptyResult<DataError.Remote>

    suspend fun getModules(clinicId: Int): Result<ArrayList<ModulesResponse>, DataError.Remote>

    suspend fun getAllPatientMedicines(
        clinicId: Int,
        patientId: Int
    ): Result<List<Medication>, DataError.Remote>

    suspend fun reportMedicationTook(
        body: MedicationReport
    ): Result<Unit, DataError.Remote>

    suspend fun setMedicationNotifications(
        results: Request
    ): Result<Unit, DataError.Remote>
}