package com.januzanj.sipmasdes.data.source.remote.response

import com.januzanj.sipmasdes.domain.model.Complaint
import com.google.gson.annotations.SerializedName

data class ComplaintsResponse(
    @SerializedName("status") val status: Int,
    @SerializedName("message") val message: String,
    @SerializedName("data") val data: Data,
    @SerializedName("error") val error: String
)

data class Data(
    @SerializedName("current_page") val currentPage: Int,
    @SerializedName("data") val data: List<Complaint>,
    @SerializedName("from") val from: Int,
    @SerializedName("last_page") val lastPage: Int,
    @SerializedName("path") val path: String,
    @SerializedName("per_page") val perPage: Int,
    @SerializedName("to") val to: Int,
    @SerializedName("total") val total: Int,
//    @SerializedName("first_page_url") val firstPageUrl: String,
//    @SerializedName("last_page_url") val lastPageUrl: String,
//    @SerializedName("next_page_url") val nextPageUrl: String,
//    @SerializedName("prev_page_url") val prevPageUrl: String,
)

