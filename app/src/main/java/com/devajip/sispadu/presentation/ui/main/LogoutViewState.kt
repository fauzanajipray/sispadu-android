package com.devajip.sispadu.presentation.ui.main

data class LogoutViewState (
    val isLoading: Boolean = false,
    val isLogout: Boolean = false,
    val message: String? = null,
    val error: String? = null
)