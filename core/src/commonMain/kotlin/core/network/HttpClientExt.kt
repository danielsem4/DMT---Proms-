package core.network

import core.domain.DataError
import core.domain.Result
import io.ktor.client.call.body
import io.ktor.client.network.sockets.SocketTimeoutException
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpStatusCode
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
        responseToResult(response) // This is where the magic happens now!
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

suspend inline fun <reified T> responseToResult(response: HttpResponse): Result<T, DataError.Remote> {
    return when (response.status) {
        HttpStatusCode.OK -> {
            val responseBody = response.bodyAsText()
            println("responseToResult: Handling 200 OK. Body received: '$responseBody'")

            // --- IMPORTANT FIX HERE ---
            // If the expected type T is Unit (as in EmptyResult for file upload success)
            // AND the server's success response is literally "200", then treat it as a success.
            if (T::class == Unit::class && responseBody == "200") {
                Result.Success(Unit as T)
            } else {
                // For other 200 OK responses, attempt to deserialize the body into type T.
                try {
                    val data = response.body<T>()
                    Result.Success(data)
                } catch (e: Exception) {
                    println("responseToResult: Deserialization error for expected type ${T::class.simpleName}: ${e.message}")
                    // Use your existing SERIALIZATION error for deserialization failures
                    Result.Error(DataError.Remote.SERIALIZATION)
                }
            }
        }
        // Handle client-side error status codes (4xx)
        HttpStatusCode.Unauthorized, HttpStatusCode.Forbidden -> {
            println("responseToResult: Unauthorized or Forbidden (${response.status.value})")
            Result.Error(DataError.Remote.FORBIDDEN)
        }
        // Handle server-side error status codes (5xx)
        in HttpStatusCode.InternalServerError..HttpStatusCode.GatewayTimeout -> {
            println("responseToResult: Server Error (${response.status.value})")
            Result.Error(DataError.Remote.SERVER)
        }
        // Handle too many requests
        HttpStatusCode.TooManyRequests -> {
            println("responseToResult: Too Many Requests (429)")
            Result.Error(DataError.Remote.TOO_MANY_REQUESTS)
        }
        // Catch-all for any other unhandled HTTP status codes
        else -> {
            val errorBody = try {
                response.bodyAsText()
            } catch (e: Exception) {
                "Could not read error body: ${e.message}"
            }
            println("responseToResult: Unhandled HTTP Status ${response.status.value}. Body: '$errorBody'")
            Result.Error(DataError.Remote.UNKNOWN)
        }
    }
}
