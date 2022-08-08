package com.januzanj.sipmasdes.data.source.remote.network

import com.januzanj.sipmasdes.data.source.remote.response.ComplaintDetailResponse
import com.januzanj.sipmasdes.data.source.remote.response.ComplaintRequest
import com.januzanj.sipmasdes.data.source.remote.response.ComplaintsResponse
import com.januzanj.sipmasdes.data.source.remote.response.Response
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface UserComplaintService {

    @Headers("Accept: application/json")
    @GET("user/complaints?private=0")
    suspend fun getComplaint(
        @Header("Authorization") token: String,
        @QueryMap query: Map<String, String>,
        @Query("page") page: Int
    ): ComplaintsResponse

    @Headers("Accept: application/json")
    @GET("user/complaints/{id}")
    suspend fun getComplaintDetail(
        @Header("Authorization") token : String,
        @Path("id") id: Int
    ): ComplaintDetailResponse

    @Headers("Accept: application/json")
    @Multipart
    @POST("user/complaints")
    suspend fun postComplaint(
        @Header("Authorization") token: String,
        @Part("user_id") user_id: RequestBody,
        @Part("title") title: RequestBody,
        @Part("description") description: RequestBody,
        @Part("position_id") position_id: RequestBody,
        @Part("is_anonymous") is_anonymous: RequestBody,
        @Part("is_private") is_private: RequestBody,
        @Part image: MultipartBody.Part,
    ): Response

    @Headers("Accept: application/json")
    @Multipart
    @POST("user/complaints")
    suspend fun postNewComplaint(
        @Header("Authorization") token: String,
        @Part user_id: MultipartBody.Part,
        @Part title: MultipartBody.Part,
        @Part description: MultipartBody.Part,
        @Part position_id: MultipartBody.Part,
        @Part is_anonymous: MultipartBody.Part,
        @Part is_private: MultipartBody.Part,
        @Part image: MultipartBody.Part
    ): retrofit2.Response<Response>

    @Headers("Accept: application/json")
    @Multipart
    @PUT("user/complaints/{id}")
    suspend fun putComplaint(
        @Header("Authorization") token: String,
        @Path("id") id: Int,
        @Body complaintRequest: ComplaintRequest
    ): Response

    @Headers("Accept: application/json")
    @DELETE("user/complaints/{id}")
    suspend fun deleteComplaint(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): Response

}