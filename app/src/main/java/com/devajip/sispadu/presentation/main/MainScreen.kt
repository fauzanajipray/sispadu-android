package com.devajip.sispadu.presentation.main

import android.content.res.Configuration
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.devajip.sispadu.presentation.components.BottomBar
import com.devajip.sispadu.presentation.history.HistoryScreen
import com.devajip.sispadu.presentation.home.HomeScreen
import com.devajip.sispadu.presentation.theme.SispaduTheme

@Composable
fun MainScreen(
    viewModel: MainViewModel = hiltViewModel(),
) {
    val selectedTab by viewModel.selectedTab
    val tabs = MainScreenTab.values()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        backgroundColor = MaterialTheme.colors.background,
        bottomBar = { BottomBar(viewModel, tabs, selectedTab) }
    ) {
        val modifier = Modifier.padding(it)

        Crossfade(selectedTab) { tab ->
            when(tab) {
                MainScreenTab.HOME -> HomeScreen()
                MainScreenTab.COMMUNITY -> HistoryScreen()
            }
        }
    }
}

@Preview("MainScreen")
@Preview("MainScreen (dark theme)", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun DefaultPreview() {
    SispaduTheme {
        MainScreen()
    }
}

