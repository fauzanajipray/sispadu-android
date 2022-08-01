package com.januzanj.sipmasdes.data.repository

import com.januzanj.sipmasdes.data.source.remote.network.AuthService
import com.januzanj.sipmasdes.data.source.remote.response.*
import com.januzanj.sipmasdes.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authService: AuthService
) : AuthRepository {
    override suspend fun postLogin(loginRequest: LoginRequest): LoginResponse {
        return authService.postLogin(loginRequest)
    }

    override suspend fun getLogout(token: String): LogoutResponse {
        return authService.getLogout(token)
    }

    override suspend fun postRegister(registerRequest: RegisterRequest): RegisterResponse {
        return authService.postRegister(registerRequest)
    }

}