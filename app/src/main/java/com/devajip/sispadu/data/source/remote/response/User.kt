package com.devajip.sispadu.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("email") val email: String,
    @SerializedName("is_email_verified") val isEmailVerified: Number,
)
