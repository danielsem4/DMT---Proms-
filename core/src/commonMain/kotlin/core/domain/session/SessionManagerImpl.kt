package core.domain.session

import core.data.model.SuccessfulLoginResponse

//class SessionManagerImpl (
//    private val prefs: SharedPreferences
//) : SessionManager {
//    override fun saveUserSession(response: SuccessfulLoginResponse) {
//        prefs.edit()
//            .putString("token", response.token)
//            .putString("fullName", response.user.first_name)
//            .apply()
//    }
//    override fun clearSession() {
//        prefs.edit().clear().apply()
//    }
//}