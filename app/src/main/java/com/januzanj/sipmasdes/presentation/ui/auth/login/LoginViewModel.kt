package com.januzanj.sipmasdes.presentation.ui.auth.login

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.januzanj.sipmasdes.common.Constant.PREF_KEY_IS_LOGIN
import com.januzanj.sipmasdes.common.Constant.PREF_KEY_USER_ID
import com.januzanj.sipmasdes.common.Constant.PREF_KEY_USER_NAME
import com.januzanj.sipmasdes.common.Constant.PREF_KEY_USER_TOKEN
import com.januzanj.sipmasdes.common.StatusMessage
import com.januzanj.sipmasdes.data.source.remote.response.LoginRequest
import com.januzanj.sipmasdes.domain.use_case.auth.GetLogoutUseCase
import com.januzanj.sipmasdes.domain.use_case.auth.PostLoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(
    private val postLoginUseCase: PostLoginUseCase,
    private val getLogoutUseCase: GetLogoutUseCase,
    private val pref: SharedPreferences,
    private val context: Context
): ViewModel() {

    private val _state = mutableStateOf(LoginViewState())
    val state: State<LoginViewState> = _state

    private val _logoutState = mutableStateOf(LogoutViewState())
    val logoutState: State<LogoutViewState> = _logoutState

    private val _isLogin = mutableStateOf(false)
    val isLogin: State<Boolean> = _isLogin

    fun setLogin(isLogin: Boolean) {
        _isLogin.value = isLogin
        if (!isLogin){
            pref.edit().clear().apply()
        }
    }

    init {
        _isLogin.value = pref.getString(PREF_KEY_IS_LOGIN, "false").toBoolean()
    }

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
                    Log.d("LoginViewModel", "login success")
                    _isLogin.value = true
                    it.data?.token?.let { it1 -> pref.edit().putString(PREF_KEY_USER_TOKEN, it1).apply() }
                    pref.edit().apply {
                        putString(PREF_KEY_IS_LOGIN, true.toString())
                        putString(PREF_KEY_USER_ID, it.data?.user?.id.toString())
                        putString(PREF_KEY_USER_NAME, it.data?.user?.name)
                    }.apply()
                }
                StatusMessage.ERROR -> {
                    _state.value = LoginViewState(error = it.message ?: "Error Occurred")
                }
                else -> {
                    _state.value = LoginViewState(error = "Error Occurred")
                }
            }
        }.launchIn(viewModelScope)
    }

    fun logout() {
        val token : String = pref.getString(PREF_KEY_USER_TOKEN, "") ?: ""
        Log.d("LogoutViewModel", "token: $token")
        if (token.isNotEmpty()) {
            getLogoutUseCase(token).onEach {
                when(it.status) {
                    StatusMessage.SUCCESS -> {
                        _logoutState.value = LogoutViewState(
                            isLoading = false,
                            isLogout = true,
                            isSuccess = true
                        )
                        pref.edit().clear().apply()
                        _isLogin.value = false
                    }
                    StatusMessage.LOADING -> _logoutState.value = LogoutViewState(isLoading = true)
                    StatusMessage.ERROR -> {
                        _logoutState.value = LogoutViewState(
                            errorMessage = it.message,
                            isError = true
                        )
                        Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                    }
                    else -> {
                        _logoutState.value = LogoutViewState(
                            errorMessage = it.message,
                            isError = true
                        )
                    }

                }
            }.launchIn(viewModelScope)
        }
    }
}