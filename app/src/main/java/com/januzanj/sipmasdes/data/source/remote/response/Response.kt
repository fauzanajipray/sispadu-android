package com.januzanj.sipmasdes.data.source.remote.response

import com.google.gson.annotations.SerializedName

class Response(
    @SerializedName("status") val status: String,
    @SerializedName("message") val message : String,
    @SerializedName("error") val error: Any?
)