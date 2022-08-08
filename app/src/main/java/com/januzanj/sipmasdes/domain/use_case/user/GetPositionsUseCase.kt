package com.januzanj.sipmasdes.domain.use_case.user

import com.google.gson.Gson
import com.januzanj.sipmasdes.common.Resource
import com.januzanj.sipmasdes.data.source.remote.response.PositionsResponse
import com.januzanj.sipmasdes.domain.repository.PublicRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetPositionsUseCase @Inject constructor(
    private val publicRepository: PublicRepository
) {
    operator fun invoke() : Flow<Resource<PositionsResponse>> = flow {
        emit(Resource.loading(null))
        try {
            val response = publicRepository.getPositions()
            emit(Resource.success(response))
        } catch (e: HttpException) {
//            val errorResponse =
//                Gson().fromJson(e.response()?.errorBody()?.string(), PositionsResponse::class.java)
            emit(Resource.error(e.message(), null))
        } catch (e: IOException) {
            emit(Resource.error(e.localizedMessage ?: "Network Error Occurred", null))
        }
    }
}