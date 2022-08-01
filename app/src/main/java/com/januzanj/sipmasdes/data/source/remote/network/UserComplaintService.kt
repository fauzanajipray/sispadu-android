package com.januzanj.sipmasdes.data.source.remote.network

import com.januzanj.sipmasdes.data.source.remote.response.ComplaintDetailResponse
import com.januzanj.sipmasdes.data.source.remote.response.ComplaintRequest
import com.januzanj.sipmasdes.data.source.remote.response.ComplaintsResponse
import com.januzanj.sipmasdes.data.source.remote.response.Response
import retrofit2.http.*

interface UserComplaintService {

    @Headers("Content-Type: application/json")
    @GET("user/complaints?private=0")
    suspend fun getComplaint(
        @Header("Authorization") token: String,
        @QueryMap query: Map<String, String>,
        @Query("page") page: Int
    ): ComplaintsResponse

    @Headers("Content-Type: application/json")
    @GET("user/complaints/{id}")
    suspend fun getComplaintDetail(
        @Header("Authorization") token : String,
        @Path("id") id: Int
    ): ComplaintDetailResponse

    @Headers("Content-Type: application/json")
    @Multipart
    @POST("user/complaints")
    suspend fun postComplaint(
        @Header("Authorization") token: String,
        @Body complaintRequest: ComplaintRequest
    ): Response

    @Headers("Content-Type: application/json")
    @Multipart
    @PUT("user/complaints/{id}")
    suspend fun putComplaint(
        @Header("Authorization") token: String,
        @Path("id") id: Int,
        @Body complaintRequest: ComplaintRequest
    ): Response

    @Headers("Content-Type: application/json")
    @DELETE("user/complaints/{id}")
    suspend fun deleteComplaint(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): Response

}