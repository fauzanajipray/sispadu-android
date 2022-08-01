package com.januzanj.sipmasdes.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class RegisterResponse(
    @SerializedName("status") val status: String,
    @SerializedName("message") val message: String,
    @SerializedName("user") val user: User,
    @SerializedName("token") val token: String,
)
