package com.januzanj.sipmasdes.presentation.ui.main

import android.content.Context
import androidx.compose.animation.*
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.januzanj.sipmasdes.presentation.ui.components.BottomBar
import com.januzanj.sipmasdes.presentation.ui.history.HistoryScreen
import com.januzanj.sipmasdes.presentation.ui.home.HomeScreen
import com.januzanj.sipmasdes.presentation.navigation.Destination
import com.januzanj.sipmasdes.presentation.ui.auth.login.LoginViewModel
import com.januzanj.sipmasdes.presentation.ui.profile.ProfileScreen

@Composable
fun MainScreen(
    viewModel: MainViewModel = hiltViewModel(),
    navController: NavController,
    context: Context,
    loginViewModel: LoginViewModel
) {
    val selectedTab by viewModel.selectedTab
    val tabs = MainScreenTab.values()

    val stateLogout by loginViewModel.logoutState

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
        Box(modifier = Modifier.fillMaxSize()){
            Crossfade(
                targetState = selectedTab,
                modifier = Modifier.fillMaxSize(),
            ) { tab ->
                when(tab) {
                    MainScreenTab.HOME -> HomeScreen(navController = navController, context = context, loginViewModel = loginViewModel)
                    MainScreenTab.HISTORY -> HistoryScreen(navController = navController)
//                MainScreenTab.NEWS -> NewsScreen(navController = navController)
                    MainScreenTab.PROFILE -> ProfileScreen(navController = navController)
                }
            }
            if (stateLogout.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                )
            }
        }
    }
}

