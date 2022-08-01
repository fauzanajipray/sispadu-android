package com.januzanj.sipmasdes.domain.use_case.auth

import com.januzanj.sipmasdes.common.Constant
import com.januzanj.sipmasdes.common.Resource
import com.januzanj.sipmasdes.data.source.remote.response.LogoutResponse
import com.januzanj.sipmasdes.domain.repository.AuthRepository
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
            val tokenWithPrefix = Constant.TOKEN_PREFIX + token
            val response = authRepository.getLogout(tokenWithPrefix)
            emit(Resource.success(response))
        } catch (e: HttpException) {
            val errorResponse = Gson().fromJson(e.response()?.errorBody()?.string(), LogoutResponse::class.java)
            emit(Resource.error(errorResponse.message , null))
        } catch (e: IOException) {
            emit(Resource.error(e.localizedMessage ?: "Network Error Occurred", null))
        }
    }
}