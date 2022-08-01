package com.januzanj.sipmasdes.domain.model

import com.google.gson.annotations.SerializedName

data class Comment(
    @SerializedName("id") val id: Int,
    @SerializedName("user_id") val userId: Int,
    @SerializedName("complaint_id") val complaintId: Int,
    @SerializedName("body") val body: String,
    @SerializedName("status") val status: String,
    @SerializedName("from_role") val fromRole: String,
    @SerializedName("created_at") val createdAt: String,
    @SerializedName("updated_at") val updatedAt: String
)