package com.devajip.sispadu.data.source.remote.response

data class RegisterRequest(
    val name: String,
    val email: String,
    val password: String,
)
