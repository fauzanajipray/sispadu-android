package com.devajip.sispadu.domain.use_case.auth

import com.devajip.sispadu.common.Resource
import com.devajip.sispadu.data.source.remote.response.LogoutResponse
import com.devajip.sispadu.domain.repository.AuthRepository
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetLogoutUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    operator fun invoke(token: String): Flow<Resource<LogoutResponse>> = flow {
        emit(Resource.loading(null))
        try {
            val response = authRepository.getLogout(token)
            emit(Resource.success(response))
        } catch (e: HttpException) {
            val errorResponse = Gson().fromJson(e.response()?.errorBody()?.string(), LogoutResponse::class.java)
            emit(Resource.error(errorResponse.message , null))
        } catch (e: IOException) {
            emit(Resource.error(e.localizedMessage ?: "Network Error Occurred", null))
        }
    }
}