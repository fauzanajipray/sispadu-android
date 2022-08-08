package com.januzanj.sipmasdes.domain.use_case.user

import android.util.Log
import com.januzanj.sipmasdes.common.Constant
import com.januzanj.sipmasdes.common.Resource
import com.januzanj.sipmasdes.data.source.remote.response.ComplaintsRequestParams
import com.januzanj.sipmasdes.data.source.remote.response.ComplaintsResponse
import com.januzanj.sipmasdes.domain.repository.ComplaintRepository
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

lateinit var response: ComplaintsResponse
class GetComplaintsUseCase @Inject constructor(
    private val complaintRepository: ComplaintRepository
) {
    operator fun invoke(
        token: String,
        query: ComplaintsRequestParams
    ): Flow<Resource<ComplaintsResponse>> = flow {
        emit(Resource.loading(null))
        try {
            val tokenWithPrefix = Constant.TOKEN_PREFIX + token
            response = complaintRepository.getComplaints(tokenWithPrefix, query, 1)
            emit(Resource.success(response))
        } catch (e: HttpException) {
            Log.e("GetComplaintsUseCase", e.toString())
            val errorResponse = Gson().fromJson(e.response()?.errorBody()?.string(), ComplaintsResponse::class.java)
            emit(Resource.error(errorResponse.message , response))
        } catch (e: IOException) {
            emit(Resource.error(e.localizedMessage ?: "Network Error Occurred", null))
        }
    }
}