package com.devajip.sispadu.presentation.auth.login

import com.devajip.sispadu.data.source.remote.response.User

data class LoginViewState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val user: User? = null,
    val token: String? = null,
    val error: String? = null
)
