package org.example.hit.heal.core.Network.session

import org.example.hit.heal.core.Network.SuccessfulLoginResponse
interface SessionManager {
    suspend fun saveUserSession(loginResponse: SuccessfulLoginResponse)
    suspend fun getUserSession(): SuccessfulLoginResponse?
    suspend fun clearUserSession()
    fun isLoggedIn(): Boolean
}
class SessionManagerImpl : SessionManager {
    private var userSession: SuccessfulLoginResponse? = null

    override suspend fun saveUserSession(loginResponse: SuccessfulLoginResponse) {
        println("Saving user session with data:")
        println("User ID: ${loginResponse.userId}")
        println("Email: ${loginResponse.email}")
        println("Name: ${loginResponse.fullName}")
        println("Clinic: ${loginResponse.clinicName}")
        println("Access Token: ${loginResponse.access}")
        println("Refresh Token: ${loginResponse.refresh}")

        userSession = loginResponse
    }

    override suspend fun getUserSession(): SuccessfulLoginResponse? {
        return userSession
    }

    override suspend fun clearUserSession() {
        userSession = null
    }

    override fun isLoggedIn(): Boolean {
        return userSession != null
    }

    // Helper functions to get specific data
    fun getAccessToken(): String? = userSession?.access
    fun getRefreshToken(): String? = userSession?.refresh
    fun getUserId(): String? = userSession?.userId
    fun getUserEmail(): String? = userSession?.email
    fun getClinicName(): String? = userSession?.clinicName
    fun getModules(): List<String> = userSession?.modules ?: emptyList()
    fun getServerUrl(): String? = userSession?.serverUrl
}