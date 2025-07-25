package core.network

import core.domain.DataError
import core.domain.Result
import io.ktor.client.call.body
import io.ktor.client.network.sockets.SocketTimeoutException
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.util.network.UnresolvedAddressException

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
        Result.Error(DataError.Remote.NOT_FOUND)
    } catch (e: Exception) {
        println("safeCall → unexpected exception: ${e::class.simpleName}: ${e.message}")
        e.printStackTrace()
        Result.Error(DataError.Remote.UNKNOWN)
    }
}

suspend inline fun <reified T> responseToResult(
    response: HttpResponse
): Result<T, DataError.Remote> {
    println("the status code is: ${response.status.value}")

    val text = response.bodyAsText()
    println("raw response body: $text")

    return when (response.status.value) {
        in 200..299 -> {
            println("HTTP 200 response body: $text")

            val parsed: T = response.body()
            Result.Success(parsed)
        }
        403 -> Result.Error(DataError.Remote.FORBIDDEN)
        404 -> Result.Error(DataError.Remote.NO_INTERNET)
        408 -> Result.Error(DataError.Remote.REQUEST_TIMEOUT)
        429 -> Result.Error(DataError.Remote.TOO_MANY_REQUESTS)
        in 500..599 -> Result.Error(DataError.Remote.SERVER)
        else -> Result.Error(DataError.Remote.UNKNOWN)
    }
}
