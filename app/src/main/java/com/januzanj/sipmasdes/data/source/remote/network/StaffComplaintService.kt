package com.januzanj.sipmasdes.data.source.remote.network

import com.januzanj.sipmasdes.data.source.remote.response.ComplaintsResponse
import retrofit2.http.*

interface StaffComplaintService {

    @Headers("Accept: application/json")
    @GET("staff/complaints")
    suspend fun getComplaint(
        @Header("Authorization") token: String,
        @QueryMap query: Map<String, String>
    ): ComplaintsResponse

    //Confirm Complaint
    @Headers("Accept: application/json")
    @POST("staff/complaints/{id}/confirm")
    suspend fun confirmComplaint(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ): ComplaintsResponse


}