package com.devajip.sispadu.data.source.remote.response

data class ComplaintsRequestParams(
    var user_id : String = "",
    var search : String = "",
    var status : String = "",
    var page : Int = 0,
)