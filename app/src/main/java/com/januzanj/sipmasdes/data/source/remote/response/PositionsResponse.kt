package com.januzanj.sipmasdes.data.source.remote.response

import com.google.gson.annotations.SerializedName
import com.januzanj.sipmasdes.domain.model.Position

data class PositionsResponse(
    @SerializedName("status") val status: Int,
    @SerializedName("message") val message: String,
    @SerializedName("data") val data: List<Position>,
    @SerializedName("errors") val error: String
)
