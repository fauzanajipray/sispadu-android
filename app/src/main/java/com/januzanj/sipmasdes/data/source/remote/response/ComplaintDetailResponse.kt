package com.januzanj.sipmasdes.data.source.remote.response

import com.januzanj.sipmasdes.domain.model.ComplaintDetail
import com.google.gson.annotations.SerializedName

data class ComplaintDetailResponse(
    @SerializedName("status") val status: Int,
    @SerializedName("message") val message: String,
    @SerializedName("data") val data: ComplaintDetail,
    @SerializedName("errors") val error: String
)
