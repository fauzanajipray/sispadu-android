package com.devajip.sispadu.presentation.ui.auth.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devajip.sispadu.data.source.remote.response.RegisterRequest
import com.devajip.sispadu.common.StatusMessage
import com.devajip.sispadu.domain.use_case.auth.PostRegisterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val postRegisterUseCase: PostRegisterUseCase
) : ViewModel() {

    fun register(registerRequest: RegisterRequest) {
        postRegisterUseCase(registerRequest).onEach {
            when(it.status) {
                StatusMessage.SUCCESS -> {

                }
            }
        }.launchIn(viewModelScope)
    }
}