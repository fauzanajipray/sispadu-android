package com.devajip.sispadu.domain.model

import com.google.gson.annotations.SerializedName

data class Complaint(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String,
    @SerializedName("status") val status: String,
    @SerializedName("created_at") val createdAt: String,
    @SerializedName("user_name") val userName: String,
    @SerializedName("position_name") val positionName: String,
    @SerializedName("comments_count") val commentsCount: Int,
)