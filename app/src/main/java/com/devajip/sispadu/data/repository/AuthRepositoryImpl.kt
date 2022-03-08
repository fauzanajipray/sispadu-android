package com.devajip.sispadu.data.repository

import com.devajip.sispadu.data.source.remote.network.AuthService
import com.devajip.sispadu.data.source.remote.response.*
import com.devajip.sispadu.domain.repository.AuthRepository
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