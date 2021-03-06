package com.devajip.sispadu.domain.repository

import com.devajip.sispadu.data.source.remote.response.*
import java.util.*

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