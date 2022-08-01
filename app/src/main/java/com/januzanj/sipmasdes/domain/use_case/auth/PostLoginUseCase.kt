package com.januzanj.sipmasdes.domain.use_case.auth

import com.januzanj.sipmasdes.common.Resource
import com.januzanj.sipmasdes.data.source.remote.response.LoginRequest
import com.januzanj.sipmasdes.data.source.remote.response.LoginResponse
import com.januzanj.sipmasdes.domain.repository.AuthRepository
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class PostLoginUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    operator fun invoke(loginRequest: LoginRequest) : Flow<Resource<LoginResponse>> = flow {
        emit(Resource.loading(null))
        try {
            val response = authRepository.postLogin(loginRequest)
            emit(Resource.success(response))
        } catch (e: HttpException) {
            val errorResponse = Gson().fromJson(e.response()?.errorBody()?.string(), LoginResponse::class.java)
            emit(Resource.error(errorResponse.message , null))
        } catch (e: IOException) {
            emit(Resource.error(e.localizedMessage ?: "Network Error Occurred", null))
        }
    }
}