package com.januzanj.sipmasdes.domain.repository

import com.januzanj.sipmasdes.data.source.remote.response.*
import okhttp3.MultipartBody
import retrofit2.Call

interface ComplaintRepository {

    suspend fun getComplaints(
        token: String,
        query: ComplaintsRequestParams,
        page: Int,
    ): ComplaintsResponse

    suspend fun getComplaintDetail(
        token: String,
        id: Int
    ): ComplaintDetailResponse

    suspend fun postComplaint(
        token: String,
        complaintRequest: ComplaintRequest
    ): Response

    suspend fun postNewComplaint(
        token: String,
        complaintRequest: ComplaintRequest
    ): retrofit2.Response<Response>

    suspend fun putComplaint(
        token: String,
        id: Int,
        complaintRequest: ComplaintRequest
    ): Response

    suspend fun deleteComplaint(
        token: String,
        id: Int
    ): Response

}