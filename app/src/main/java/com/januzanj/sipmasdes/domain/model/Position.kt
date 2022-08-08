package com.januzanj.sipmasdes.domain.model

import com.google.gson.annotations.SerializedName

data class Position(
    @SerializedName("id") val id: Int = 0,
    @SerializedName("name") val name: String = "",
    @SerializedName("description") val description: String = ""
)
