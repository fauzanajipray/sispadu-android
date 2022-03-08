package com.devajip.sispadu.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("status") val status: Int,
    @SerializedName("message") val message: String,
    @SerializedName("user") val user: User,
    @SerializedName("token") val token: String,
    @SerializedName("error") val error: String
)
