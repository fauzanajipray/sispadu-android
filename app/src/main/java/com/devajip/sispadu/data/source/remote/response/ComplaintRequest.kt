package com.devajip.sispadu.data.source.remote.response

import okhttp3.MultipartBody

data class ComplaintRequest(
    val user_id: Int,
    val title: String,
    val description: String,
    val position_id: Int,
    val image: MultipartBody.Part,
    val is_anonymous: Int,
    val is_private: Int,
)
