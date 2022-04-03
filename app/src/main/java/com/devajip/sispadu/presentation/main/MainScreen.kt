package com.devajip.sispadu.presentation.main

import androidx.compose.animation.*
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.Key.Companion.Home
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.devajip.sispadu.presentation.components.BottomBar
import com.devajip.sispadu.presentation.history.HistoryScreen
import com.devajip.sispadu.presentation.home.HomeScreen
import com.devajip.sispadu.presentation.navigation.Destination
import com.devajip.sispadu.presentation.news.NewsScreen
import com.devajip.sispadu.presentation.profile.ProfileScreen

@Composable
fun MainScreen(
    viewModel: MainViewModel = hiltViewModel(),
    navController: NavController
) {
    val selectedTab by viewModel.selectedTab
    val tabs = MainScreenTab.values()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        backgroundColor = MaterialTheme.colors.background,
        bottomBar = {
            BottomBar(viewModel, tabs, selectedTab)
        },
        floatingActionButtonPosition = FabPosition.End,
        floatingActionButton = {
            if (selectedTab == MainScreenTab.HOME) {
                FloatingActionButton(
                    onClick = {
                        navController.navigate(Destination.AddComplaint.route)
                    },
                    backgroundColor = MaterialTheme.colors.primary,
                ) {
                    Icon(imageVector = Icons.Filled.Add, contentDescription = "Add", tint = MaterialTheme.colors.onPrimary)
                }
            }
        }
    ) {
        Crossfade(selectedTab) { tab ->
            when(tab) {
                MainScreenTab.HOME -> HomeScreen(navController = navController)
                MainScreenTab.HISTORY -> HistoryScreen(navController = navController)
//                MainScreenTab.NEWS -> NewsScreen(navController = navController)
                MainScreenTab.PROFILE -> ProfileScreen(navController = navController)
            }
        }
    }
}

