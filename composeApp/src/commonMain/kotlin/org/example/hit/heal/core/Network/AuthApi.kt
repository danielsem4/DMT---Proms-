package org.example.hit.heal.core.Network



interface AuthApi {
    suspend fun login(loginRequest: LoginRequest): NetworkResult<LoginResponse>
}