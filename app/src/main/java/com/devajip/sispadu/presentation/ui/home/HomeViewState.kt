package com.devajip.sispadu.presentation.ui.home

import com.devajip.sispadu.domain.model.Complaint

class HomeViewState(
    var isLoading: Boolean = false,
    var isSuccess: Boolean = false,
    var complaints: List<Complaint> = ArrayList(),
    var perPage: Int = 0,
    var page: Int = 1,
    var totalPages: Int = 0,
    var error: String? = null
)