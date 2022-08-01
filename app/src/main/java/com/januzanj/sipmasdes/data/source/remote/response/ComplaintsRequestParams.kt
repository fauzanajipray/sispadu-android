package com.januzanj.sipmasdes.data.source.remote.response

data class ComplaintsRequestParams(
    var user_id : String = "",
    var search : String = "",
    var status : String = "",
)