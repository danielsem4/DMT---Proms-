package core.domain.use_case

import core.data.model.SuccessfulLoginResponse
import core.domain.DataError
import core.domain.api.AppApi
import core.domain.Result


class LoginUseCase(
    private val api: AppApi
) {
    suspend fun execute(
        email: String,
        password: String
    ): Result<SuccessfulLoginResponse, DataError.Remote> {
        return api.login(email, password)
    }
}