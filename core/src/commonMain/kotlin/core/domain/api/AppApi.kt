package core.domain.api

import core.data.model.SuccessfulLoginResponse
import core.domain.DataError
import core.domain.Result


interface AppApi {
    suspend fun login(
        email: String,
        password: String
    ): Result<SuccessfulLoginResponse, DataError.Remote>
}