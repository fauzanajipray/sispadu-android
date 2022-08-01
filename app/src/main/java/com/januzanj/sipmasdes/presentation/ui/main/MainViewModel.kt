package com.januzanj.sipmasdes.presentation.ui.main

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(

) : ViewModel() {

    private val _selectedTab: MutableState<MainScreenTab> =
        mutableStateOf(MainScreenTab.HOME)
    val selectedTab: State<MainScreenTab> get() = _selectedTab

    fun selectTab(tab: MainScreenTab) {
        _selectedTab.value = tab
    }

}