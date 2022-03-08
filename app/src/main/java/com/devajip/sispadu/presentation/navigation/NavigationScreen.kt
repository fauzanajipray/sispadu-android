package com.devajip.sispadu.presentation.navigation

import android.content.Context
import android.content.SharedPreferences
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.devajip.sispadu.presentation.auth.login.LoginScreen
import com.devajip.sispadu.presentation.auth.login.LoginViewModel
import com.devajip.sispadu.presentation.home.HomeScreen
import com.devajip.sispadu.common.Constant

@Composable
fun NavigationScreen() {

    val navController = rememberNavController()
    var pref: SharedPreferences = LocalContext.current.getSharedPreferences(Constant.PREF_NAME, Context.MODE_PRIVATE)
    val token = pref.getString(Constant.PREF_KEY_USER_TOKEN, "")

    val startDestination = if (token.isNullOrEmpty()) {
        Destination.Login.route
    } else {
        Destination.Home.route
    }

    NavHost(
        navController = navController,
        startDestination = if (token.isNullOrEmpty()) Destination.Login.route else Destination.Home.route
    ) {
        composable(route = Destination.Login.route) {
            val viewModel = hiltViewModel<LoginViewModel>()
            if (viewModel.state.value.isSuccess){
                LaunchedEffect(key1 = Unit) {
                    navController.navigate(Destination.Home.route) {
                        popUpTo(Destination.Login.route) {
                            inclusive = true
                        }
                    }
                }
            } else {
                LoginScreen()
            }
        }
        composable(route = Destination.Home.route) {
            HomeScreen()
        }
    }

}