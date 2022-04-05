package com.devajip.sispadu.data.repository

import com.devajip.sispadu.data.source.remote.network.UserComplaintService
import com.devajip.sispadu.data.source.remote.response.*
import com.devajip.sispadu.domain.repository.ComplaintRepository
import javax.inject.Inject

class ComplaintRepositoryImpl @Inject constructor(
    private val complaintService: UserComplaintService
): ComplaintRepository {
    override suspend fun getComplaints(
        token: String,
        query: ComplaintsRequestParams,
        page: Int
    ): ComplaintsResponse {
        val queryMap : Map<String, String> = mapOf(
            "user_id" to query.user_id,
            "search" to query.search,
            "status" to query.status,
        )
        return complaintService.getComplaint(token, queryMap, page)
    }

    override suspend fun getComplaintDetail(
        token: String,
        id: Int
    ): ComplaintDetailResponse {
        return complaintService.getComplaintDetail(token, id)
    }

    override suspend fun postComplaint(
        token: String,
        complaintRequest: ComplaintRequest
    ): Response {
        return complaintService.postComplaint(token, complaintRequest)
    }

    override suspend fun putComplaint(
        token: String,
        id: Int,
        complaintRequest: ComplaintRequest
    ): Response {
        return complaintService.putComplaint(token, id, complaintRequest)
    }

    override suspend fun deleteComplaint(token: String, id: Int): Response {
        return complaintService.deleteComplaint(token, id)
    }

}