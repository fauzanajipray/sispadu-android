package com.devajip.sispadu.domain.model

import com.google.gson.annotations.SerializedName

data class ComplaintDetail(
    @SerializedName("id"            ) var id           : Int? = null,
    @SerializedName("user_id"       ) var userId       : Int? = null,
    @SerializedName("title"         ) var title        : String? = null,
    @SerializedName("description"   ) var description  : String? = null,
    @SerializedName("position_id"   ) var positionId   : Int? = null,
    @SerializedName("image"         ) var image        : String? = null,
    @SerializedName("status"        ) var status       : String? = null,
    @SerializedName("is_anonymous"  ) var isAnonymous  : Int? = null,
    @SerializedName("is_private"    ) var isPrivate    : Int? = null,
    @SerializedName("created_at"    ) var createdAt    : String? = null,
    @SerializedName("username"      ) var username     : String? = null,
    @SerializedName("position_name" ) var positionName : String? = null,
    @SerializedName("comments"      ) var comments     : List<Comment?> = arrayListOf()
)