package com.januzanj.sipmasdes.presentation.ui.add_complaint

data class AddComplaintState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = "",
    val isSuccess: Boolean = false
)
