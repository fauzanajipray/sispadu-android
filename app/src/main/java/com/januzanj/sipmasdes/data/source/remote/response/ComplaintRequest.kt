package com.januzanj.sipmasdes.data.source.remote.response

import okhttp3.MultipartBody
import java.io.File

data class ComplaintRequest(
    val user_id: Int,
    val title: String,
    val description: String,
    val position_id: Int? = null,
    val image: File,
    val is_anonymous: Int,
    val is_private: Int,
)
