package com.devajip.sispadu.presentation.ui.home.detail

import com.devajip.sispadu.domain.model.ComplaintDetail

data class ComplaintDetailState(
    var complaint: ComplaintDetail? = null,
    var isLoading: Boolean = false,
    var isSuccess: Boolean = false,
    var error: Any? = null
)