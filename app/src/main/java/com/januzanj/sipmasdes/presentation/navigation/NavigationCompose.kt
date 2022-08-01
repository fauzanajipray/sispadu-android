package com.januzanj.sipmasdes.presentation.navigation

import android.content.Context
import android.content.SharedPreferences
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.compose.material.MaterialTheme
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.januzanj.sipmasdes.presentation.ui.auth.login.LoginScreen
import com.januzanj.sipmasdes.presentation.ui.auth.login.LoginViewModel
import com.januzanj.sipmasdes.presentation.ui.main.MainScreen
import com.januzanj.sipmasdes.common.Constant
import com.januzanj.sipmasdes.presentation.ui.add_complaint.AddComplaintScreen
import com.januzanj.sipmasdes.presentation.ui.add_complaint.camera.CameraScreen
import com.januzanj.sipmasdes.presentation.ui.add_complaint.camera.CameraX
import com.januzanj.sipmasdes.presentation.ui.home.detail.ComplaintDetailScreen
import com.januzanj.sipmasdes.presentation.ui.main.LogoutViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.januzanj.sipmasdes.presentation.ui.add_complaint.camera.CameraViewModel

@Composable
fun NavigationCompose(
    context: Context,
    lifecycleOwner: LifecycleOwner
) {
    // Remember a SystemUiController
    val systemUiController = rememberSystemUiController()
    val useDarkIcons = MaterialTheme.colors.isLight
    val primaryVariantColor = MaterialTheme.colors.primaryVariant
    val isSystemInDarkTheme = isSystemInDarkTheme()

    var cameraX: CameraX = CameraX(context, lifecycleOwner)

    SideEffect {
        // Update all of the system bar colors to be transparent, and use
        // dark icons if we're in light theme
        systemUiController.setSystemBarsColor(
            color = if (isSystemInDarkTheme) Color.DarkGray else primaryVariantColor,
            darkIcons = useDarkIcons
        )
        systemUiController.setStatusBarColor(
            color = if (isSystemInDarkTheme) Color.DarkGray else Color.Transparent,
            darkIcons = useDarkIcons
        )
        // setStatusBarsColor() and setNavigationBarColor() also exist
    }

    val navController = rememberNavController()
    val pref: SharedPreferences = LocalContext.current.getSharedPreferences(Constant.PREF_NAME, Context.MODE_PRIVATE)
    val token = pref.getString(Constant.PREF_KEY_USER_TOKEN, "")

    val cameraViewModel: CameraViewModel = viewModel()
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
            val viewModel :  LogoutViewModel = hiltViewModel()
            if (viewModel.state.value.isLogout){
                LaunchedEffect(key1 = Unit) {
                    navController.navigate(Destination.Login.route) {
                        popUpTo(Destination.Home.route) {
                            inclusive = true
                        }
                    }
                }
            } else {
                MainScreen(navController = navController)
            }
        }
        composable(route = Destination.ComplaintDetail.route.plus("/{complaintId}"),
            arguments = listOf(
                navArgument("complaintId"){
                    type = NavType.IntType
                }
            )
        ) {
            it.arguments?.getInt("complaintId")?.let { it1 ->
                ComplaintDetailScreen(
                    navController = navController,
                    complaintId = it1
                )
            }
        }
        composable(route = Destination.AddComplaint.route) {
            AddComplaintScreen(
                navController = navController,
                cameraViewModel = cameraViewModel
            )
        }
        composable(route = Destination.Camera.route){
            CameraScreen(
                navController = navController,
                cameraX = cameraX,
                cameraViewModel
            )
        }
    }

}