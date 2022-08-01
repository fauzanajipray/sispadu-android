package com.januzanj.sipmasdes.data.source.remote.response

data class RegisterRequest(
    val name: String,
    val email: String,
    val password: String,
)
