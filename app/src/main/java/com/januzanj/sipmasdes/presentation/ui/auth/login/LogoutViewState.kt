package com.januzanj.sipmasdes.presentation.ui.auth.login


data class LogoutViewState (
    val isLoading: Boolean = false,
    val isLogout: Boolean = false,
    val isSuccess: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String? = null
)