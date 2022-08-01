package com.januzanj.sipmasdes.domain.repository

import com.januzanj.sipmasdes.data.source.remote.response.*

interface AuthRepository {

    suspend fun postLogin(loginRequest: LoginRequest): LoginResponse

    suspend fun getLogout(token: String): LogoutResponse

    suspend fun postRegister(registerRequest: RegisterRequest): RegisterResponse

}