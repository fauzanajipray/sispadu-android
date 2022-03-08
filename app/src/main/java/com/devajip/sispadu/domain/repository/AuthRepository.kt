package com.devajip.sispadu.domain.repository

import com.devajip.sispadu.data.source.remote.response.*

interface AuthRepository {

    suspend fun postLogin(loginRequest: LoginRequest): LoginResponse

    suspend fun getLogout(token: String): LogoutResponse

    suspend fun postRegister(registerRequest: RegisterRequest): RegisterResponse

}