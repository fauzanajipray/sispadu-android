package com.januzanj.sipmasdes.data.source.remote.network

import com.januzanj.sipmasdes.data.source.remote.response.PositionsResponse
import retrofit2.http.GET
import retrofit2.http.Headers

interface PublicService {

    @Headers("Accept: application/json")
    @GET("positions")
    suspend fun getPositions(): PositionsResponse
}