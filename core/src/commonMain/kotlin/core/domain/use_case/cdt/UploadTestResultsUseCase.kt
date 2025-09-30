package core.domain.use_case.cdt

import core.domain.DataError
import core.domain.Result
import core.domain.api.AppApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json

val json = Json {
    encodeDefaults = true
    explicitNulls = true
}

class UploadTestResultsUseCase(
    val api: AppApi
) {
    suspend inline fun <reified T> execute(results: T): Result<String, DataError.Remote> {
        return try {
            val jsonBody = json.encodeToString(results)
            println(jsonBody)
            api.sendResults(jsonBody)
        } catch (e: Exception) {
            Result.Error(DataError.Remote.UNKNOWN)
        }
    }
}