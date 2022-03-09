package com.devajip.sispadu.presentation.main

import com.devajip.sispadu.data.source.remote.response.User

data class LogoutViewState (
    val isLoading: Boolean = false,
    val isLogout: Boolean = false,
    val message: String? = null,
    val error: String? = null
)