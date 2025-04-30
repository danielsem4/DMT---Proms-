package core.domain.session

import core.data.model.SuccessfulLoginResponse

interface SessionManager {

    fun saveUserSession(response: SuccessfulLoginResponse)
    fun clearSession()
}
