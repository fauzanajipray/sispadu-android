package com.januzanj.sipmasdes.presentation.ui.auth.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.januzanj.sipmasdes.data.source.remote.response.RegisterRequest
import com.januzanj.sipmasdes.common.StatusMessage
import com.januzanj.sipmasdes.domain.use_case.auth.PostRegisterUseCase
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