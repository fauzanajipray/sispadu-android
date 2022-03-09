package com.devajip.sispadu.presentation.main

import android.content.SharedPreferences
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devajip.sispadu.common.Constant
import com.devajip.sispadu.common.StatusMessage
import com.devajip.sispadu.domain.use_case.auth.GetLogoutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class LogoutViewModel @Inject constructor(
    private val getLogoutUseCase: GetLogoutUseCase,
    private val pref: SharedPreferences,
) : ViewModel() {

    private val _state = mutableStateOf(LogoutViewState())
    val state: State<LogoutViewState> = _state

    fun logout() {
        val token : String = pref.getString(Constant.PREF_KEY_USER_TOKEN, "") ?: ""
        Log.d("LogoutViewModel", "token: $token")
        if (token.isNotEmpty()) {
            getLogoutUseCase(token).onEach {
                when(it.status) {
                    StatusMessage.SUCCESS -> {
                        _state.value = LogoutViewState(
                            isLoading = false,
                            isLogout = true
                        )
                        pref.edit().clear().apply()
                    }
                    StatusMessage.ERROR -> {
                        _state.value = LogoutViewState(
                            error = it.message
                        )
                    }
                    StatusMessage.LOADING -> _state.value = LogoutViewState(isLoading = true)
                }
            }.launchIn(viewModelScope)
        }
    }

}