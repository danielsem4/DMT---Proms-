package core.network

import core.data.storage.Storage
import core.domain.DataError
import core.util.PrefKeys
import io.ktor.client.HttpClient
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.http.HttpHeaders

/**
 * Extension functions for HttpClient to handle authenticated requests
 * with token from storage.
 */

suspend inline fun <reified T> HttpClient.getWithAuth(
    url: String,
    storage: Storage,
    block: HttpRequestBuilder.() -> Unit = {}
): core.domain.Result<T, DataError.Remote> = safeCall {
    get(url) {
        storage.get(PrefKeys.token)
            ?.takeIf { it.isNotBlank() }
            ?.let {
                header(HttpHeaders.Authorization, "token $it")
                println("🔐 Using token: $it")
            }

        block()
    }.also { response ->
        println("📤 Sent GET request to: $url")
        println("📥 Response status: ${response.status}")
        println("📦 Response headers:")
        response.headers.forEach { name, values ->
            println("   ▶ $name: ${values.joinToString()}")
        }
    }
}



suspend inline fun <reified T> HttpClient.postWithAuth(
    url: String,
    storage: Storage,
    block: HttpRequestBuilder.() -> Unit = {}
): core.domain.Result<T, DataError.Remote> = safeCall {
    post(url) {
        println("postWithAuth: $url")
        storage.get(PrefKeys.token)
            ?.takeIf { it.isNotBlank() }
            ?.let { header(HttpHeaders.Authorization, "token $it")
                println("postWithAuth token: $it")}
        block()
    }
}