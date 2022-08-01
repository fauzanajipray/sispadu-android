package com.januzanj.sipmasdes.domain.use_case.user

import com.januzanj.sipmasdes.common.Constant
import com.januzanj.sipmasdes.common.Resource
import com.januzanj.sipmasdes.data.source.remote.response.ComplaintRequest
import com.januzanj.sipmasdes.data.source.remote.response.Response
import com.januzanj.sipmasdes.domain.repository.ComplaintRepository
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class PostComplaintUseCase @Inject constructor(
    private val complaintRepository: ComplaintRepository
) {
    operator fun invoke(
        token: String,
        complaintRequest: ComplaintRequest
    ): Flow<Resource<Response>> = flow {
        emit(Resource.loading(null))
        try {
            val tokenWithPrefix = Constant.TOKEN_PREFIX + token
            val response = complaintRepository.postComplaint(tokenWithPrefix, complaintRequest)
            emit(Resource.success(response))
        } catch (e: HttpException) {
            val errorResponse = Gson().fromJson(e.response()?.errorBody()?.string(), Response::class.java)
            emit(Resource.error(errorResponse.message , null))
        } catch (e: IOException) {
            emit(Resource.error(e.localizedMessage ?: "Network Error Occurred", null))
        }
    }
}