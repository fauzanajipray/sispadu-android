package com.devajip.sispadu.domain.model

import com.google.gson.annotations.SerializedName

data class ComplaintDetail(
    @SerializedName("id"            ) var id           : Int,
    @SerializedName("user_id"       ) var userId       : Int,
    @SerializedName("title"         ) var title        : String,
    @SerializedName("description"   ) var description  : String,
    @SerializedName("position_id"   ) var positionId   : Int,
    @SerializedName("image"         ) var image        : String? = null,
    @SerializedName("status"        ) var status       : String,
    @SerializedName("is_anonymous"  ) var isAnonymous  : Int = 0,
    @SerializedName("is_private"    ) var isPrivate    : Int = 0,
    @SerializedName("created_at"    ) var createdAt    : String,
    @SerializedName("username"      ) var userName     : String,
    @SerializedName("position_name" ) var positionName : String,
    @SerializedName("comments"      ) var comments     : List<Comment?> = arrayListOf()
)