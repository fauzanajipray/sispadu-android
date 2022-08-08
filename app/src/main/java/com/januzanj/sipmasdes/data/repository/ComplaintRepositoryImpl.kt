package com.januzanj.sipmasdes.data.repository

import android.util.Log
import com.google.gson.JsonObject
import com.januzanj.sipmasdes.data.source.remote.network.UserComplaintService
import com.januzanj.sipmasdes.data.source.remote.response.*
import com.januzanj.sipmasdes.domain.repository.ComplaintRepository
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
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
        val userId : RequestBody = complaintRequest.user_id.toString().toRequestBody("text/plain".toMediaTypeOrNull())
        val title : RequestBody = complaintRequest.title.toRequestBody("text/plain".toMediaTypeOrNull())
        val description : RequestBody = complaintRequest.description.toRequestBody("text/plain".toMediaTypeOrNull())
        val positionId : RequestBody = complaintRequest.position_id.toString().toRequestBody("text/plain".toMediaTypeOrNull())
        val isAnonymous : RequestBody = complaintRequest.is_anonymous.toString().toRequestBody("text/plain".toMediaTypeOrNull())
        val isPrivate : RequestBody = complaintRequest.is_private.toString().toRequestBody("text/plain".toMediaTypeOrNull())
        val image = MultipartBody.Part.createFormData( "image", complaintRequest.image.name, complaintRequest.image.asRequestBody("image/*".toMediaTypeOrNull()) )

        val jsonObject = JsonObject()
        jsonObject.addProperty("user_id", complaintRequest.user_id)
        jsonObject.addProperty("title", complaintRequest.title)
        jsonObject.addProperty("description", complaintRequest.description)
        jsonObject.addProperty("position_id", complaintRequest.position_id)
        jsonObject.addProperty("is_anonymous", complaintRequest.is_anonymous)
        jsonObject.addProperty("is_private", complaintRequest.is_private)

        val body = jsonObject.toString().toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
        Log.d("ComplaintRepositoryImpl", "JSON : $jsonObject")

//        Log.d("ComplaintRepositoryImpl", "userId: $userId")
//        Log.d("ComplaintRepositoryImpl", "title: $title")
//        Log.d("ComplaintRepositoryImpl", "description: $description")
//        Log.d("ComplaintRepositoryImpl", "positionId: $positionId")
//        Log.d("ComplaintRepositoryImpl", "isAnonymous: $isAnonymous")
//        Log.d("ComplaintRepositoryImpl", "isPrivate: $isPrivate")

        Log.d("ComplaintRepositoryImpl", "token: $token")
        Log.d("ComplaintRepositoryImpl", "image: ${complaintRequest.image}")

        return complaintService.postComplaint(
            token = token,
            userId,
            title,
            description,
            positionId,
            isAnonymous,
            isPrivate,
            image = image
        )
    }

    override suspend fun postNewComplaint(
        token: String,
        complaintRequest: ComplaintRequest
    ): retrofit2.Response<Response> {
//        val userId: RequestBody = complaintRequest.user_id.toString().toRequestBody("text/plain".toMediaTypeOrNull())
//        val title : RequestBody = complaintRequest.title.toRequestBody("text/plain".toMediaTypeOrNull())
//        val description : RequestBody = complaintRequest.description.toRequestBody("text/plain".toMediaTypeOrNull())
//        val positionId : RequestBody = complaintRequest.position_id.toString().toRequestBody("text/plain".toMediaTypeOrNull())
//        val isAnonymous : RequestBody = complaintRequest.is_anonymous.toString().toRequestBody("text/plain".toMediaTypeOrNull())
//        val isPrivate : RequestBody = complaintRequest.is_private.toString().toRequestBody("text/plain".toMediaTypeOrNull())
        var userId = MultipartBody.Part.createFormData("user_id", complaintRequest.user_id.toString())
        val title = MultipartBody.Part.createFormData("title", complaintRequest.title)
        val description = MultipartBody.Part.createFormData("description", complaintRequest.description)
        val positionId = MultipartBody.Part.createFormData("position_id", complaintRequest.position_id.toString())
        val isAnonymous = MultipartBody.Part.createFormData("is_anonymous", complaintRequest.is_anonymous.toString())
        val isPrivate = MultipartBody.Part.createFormData("is_private", complaintRequest.is_private.toString())
        val image = MultipartBody.Part.createFormData( "image", complaintRequest.image.name, complaintRequest.image.asRequestBody("image/*".toMediaTypeOrNull()) )
        return complaintService.postNewComplaint(
            token = token,
            userId,
            title,
            description,
            positionId,
            isAnonymous,
            isPrivate,
            image = image
        )
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