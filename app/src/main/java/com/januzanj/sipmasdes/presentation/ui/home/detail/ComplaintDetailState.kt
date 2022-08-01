package com.januzanj.sipmasdes.presentation.ui.home.detail

import com.januzanj.sipmasdes.domain.model.ComplaintDetail

data class ComplaintDetailState(
    var complaint: ComplaintDetail? = null,
    var isLoading: Boolean = false,
    var isSuccess: Boolean = false,
    var error: Any? = null
)