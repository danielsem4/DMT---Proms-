package core.network

import core.domain.DataError
import core.domain.Result
import io.ktor.client.call.body
import io.ktor.client.network.sockets.SocketTimeoutException
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.coroutines.ensureActive
import kotlinx.serialization.json.Json
import kotlin.coroutines.coroutineContext

/**
 * A safe call function that executes a network request and handles exceptions.
 * It returns a Result type that can either be a success with the expected type T
 */

suspend inline fun <reified T> safeCall(
    execute: () -> HttpResponse
): Result<T, DataError.Remote> {
    return try {
        val response = execute()
        responseToResult(response)
    } catch (e: SocketTimeoutException) {
        println("safeCall → timeout: ${e.message}")
        Result.Error(DataError.Remote.REQUEST_TIMEOUT)
    } catch (e: UnresolvedAddressException) {
        println("safeCall → no internet: ${e.message}")
        Result.Error(DataError.Remote.NO_INTERNET)
    } catch (e: Exception) {
        println("safeCall → unexpected exception: ${e::class.simpleName}: ${e.message}")
        e.printStackTrace()
        Result.Error(DataError.Remote.UNKNOWN)
    }
}

val json = Json { ignoreUnknownKeys = true; isLenient = true }

suspend inline fun <reified T> responseToResult(
    response: HttpResponse
): Result<T, DataError.Remote> {
    println("the status code is: ${response.status.value}")

    val text = response.bodyAsText()
    println("raw response body: $text")

    return when (response.status.value) {
        in 200..299 -> {
            runCatching {
                json.decodeFromString<T>(text)
            }.fold(
                onSuccess = { Result.Success(it) },
                onFailure = { err ->
                    println("deserialization error: ${err.message}")
                    Result.Error(DataError.Remote.SERIALIZATION)
                }
            )
        }
        403 -> Result.Error(DataError.Remote.FORBIDDEN)
        404 -> Result.Error(DataError.Remote.NO_INTERNET)
        408 -> Result.Error(DataError.Remote.REQUEST_TIMEOUT)
        429 -> Result.Error(DataError.Remote.TOO_MANY_REQUESTS)
        in 500..599 -> Result.Error(DataError.Remote.SERVER)
        else -> Result.Error(DataError.Remote.UNKNOWN)
    }
}
