package org.example.hit.heal.core.Network



import org.example.hit.heal.core.Network.NetworkResult
interface AuthApi {
    suspend fun login(loginRequest: LoginRequest): NetworkResult<SuccessfulLoginResponse>
}