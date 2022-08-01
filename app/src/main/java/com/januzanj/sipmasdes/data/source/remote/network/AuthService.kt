package com.januzanj.sipmasdes.data.source.remote.network

import com.januzanj.sipmasdes.data.source.remote.response.*
import retrofit2.http.*

interface AuthService {

    @Headers("Content-Type: application/json")
    @POST("login")
    suspend fun postLogin(
        @Body loginRequest: LoginRequest
    ): LoginResponse

    @Headers("Content-Type: application/json")
    @GET("logout")
    suspend fun getLogout(
        @Header("Authorization") token: String
    ): LogoutResponse

    // Register
    @Headers("Content-Type: application/json")
    @POST("register")
    suspend fun postRegister(
        @Body registerRequest: RegisterRequest
    ): RegisterResponse

}