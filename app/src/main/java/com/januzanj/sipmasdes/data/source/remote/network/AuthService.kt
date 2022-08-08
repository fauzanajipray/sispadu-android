package com.januzanj.sipmasdes.data.source.remote.network

import com.januzanj.sipmasdes.data.source.remote.response.*
import retrofit2.http.*

interface AuthService {

    @Headers("Accept: application/json")
    @POST("login")
    suspend fun postLogin(
        @Body loginRequest: LoginRequest
    ): LoginResponse

    @Headers("Accept: application/json")
    @GET("logout")
    suspend fun getLogout(
        @Header("Authorization") token: String
    ): LogoutResponse

    // Register
    @Headers("Accept: application/json")
    @POST("register")
    suspend fun postRegister(
        @Body registerRequest: RegisterRequest
    ): RegisterResponse

}