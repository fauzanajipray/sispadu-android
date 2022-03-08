package com.devajip.sispadu.presentation.home

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import com.devajip.sispadu.domain.use_case.auth.GetLogoutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LogoutViewModel @Inject constructor(
    private val getLogoutUseCase: GetLogoutUseCase,
    private val pref: SharedPreferences,
) : ViewModel() {

    fun logout() {

    }

}