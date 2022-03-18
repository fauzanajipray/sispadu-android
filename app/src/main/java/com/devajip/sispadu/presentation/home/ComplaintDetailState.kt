package com.devajip.sispadu.presentation.home

import com.devajip.sispadu.domain.model.ComplaintDetail

data class ComplaintDetailState(
    var complaint: ComplaintDetail? = null,
    var isLoading: Boolean = false,
    var isSuccess: Boolean = false,
    var error: Any? = null
)