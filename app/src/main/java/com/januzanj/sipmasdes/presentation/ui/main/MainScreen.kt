package com.januzanj.sipmasdes.presentation.ui.main

import androidx.compose.animation.*
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.januzanj.sipmasdes.presentation.ui.components.BottomBar
import com.januzanj.sipmasdes.presentation.ui.history.HistoryScreen
import com.januzanj.sipmasdes.presentation.ui.home.HomeScreen
import com.januzanj.sipmasdes.presentation.navigation.Destination
import com.januzanj.sipmasdes.presentation.ui.profile.ProfileScreen

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

