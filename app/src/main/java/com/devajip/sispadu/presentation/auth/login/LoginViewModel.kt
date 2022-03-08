package com.devajip.sispadu.presentation.auth.login

import android.content.SharedPreferences
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devajip.sispadu.common.Constant.PREF_KEY_USER_TOKEN
import com.devajip.sispadu.common.StatusMessage
import com.devajip.sispadu.data.source.remote.response.LoginRequest
import com.devajip.sispadu.domain.use_case.auth.PostLoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(
    private val postLoginUseCase: PostLoginUseCase,
    private  val savedStateHandle: SavedStateHandle,
    private val pref: SharedPreferences,
): ViewModel() {


    private val _state = mutableStateOf(LoginViewState())
    val state: State<LoginViewState> = _state

    fun login(loginRequest: LoginRequest) {
        postLoginUseCase(loginRequest).onEach {
            when(it.status){
                StatusMessage.LOADING -> {
                    _state.value = LoginViewState(isLoading = true)
                }
                StatusMessage.SUCCESS -> {
                    _state.value = LoginViewState(
                        isLoading = false,
                        isSuccess = true,
                        user = it.data?.user,
                        token = it.data?.token
                    )
                    it.data?.token?.let { it1 -> pref.edit().putString(PREF_KEY_USER_TOKEN, it1).apply() }
                    pref.edit()
                }
                StatusMessage.ERROR -> {
                    _state.value = LoginViewState(error = it.message ?: "Error Occurred")
                }
            }
        }.launchIn(viewModelScope)
    }

}