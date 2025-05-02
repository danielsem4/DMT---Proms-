package core.data.remote.impl

import core.data.model.LoginRequest
import core.data.model.SuccessfulLoginResponse
import core.domain.DataError
import core.domain.api.AppApi
import core.network.safeCall

import io.ktor.client.HttpClient
import io.ktor.client.request.post
import core.domain.Result
import core.network.AppConfig.BASE_URL
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType


class GenericApiImpl () {

}

class KtorAppRemoteDataSource(
    private val httpClient: HttpClient
): AppApi {

    override suspend fun login(email: String, password: String):
            Result<SuccessfulLoginResponse, DataError.Remote> = safeCall {
        httpClient.post("$BASE_URL/login/") {
            contentType(ContentType.Application.Json)
            setBody(LoginRequest(email, password))
        }
    }

}