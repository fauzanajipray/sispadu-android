package com.devajip.sispadu.domain.use_case.user

import com.devajip.sispadu.common.Constant
import com.devajip.sispadu.common.Resource
import com.devajip.sispadu.data.source.remote.response.ComplaintDetailResponse
import com.devajip.sispadu.domain.repository.ComplaintRepository
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetComplaintDetailUseCase @Inject constructor(
    private val complaintRepository: ComplaintRepository
) {
    operator fun invoke(
        token: String,
        id: Int
    ): Flow<Resource<ComplaintDetailResponse>> = flow {
        emit(Resource.loading(null))
        try {
            val tokenWithPrefix = Constant.TOKEN_PREFIX + token
            val response = complaintRepository.getComplaintDetail(tokenWithPrefix, id)
            emit(Resource.success(response))
        } catch (e: HttpException) {
            val errorResponse = Gson().fromJson(e.response()?.errorBody()?.string(), ComplaintDetailResponse::class.java)
            emit(Resource.error(errorResponse.message , null))
        } catch (e: IOException) {
            emit(Resource.error(e.localizedMessage ?: "Network Error Occurred", null))
        }
    }
}