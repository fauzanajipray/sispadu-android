package com.januzanj.sipmasdes.presentation.navigation

import android.content.Context
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.getValue
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.januzanj.sipmasdes.presentation.ui.auth.login.LoginScreen
import com.januzanj.sipmasdes.presentation.ui.auth.login.LoginViewModel
import com.januzanj.sipmasdes.presentation.ui.main.MainScreen
import com.januzanj.sipmasdes.presentation.ui.add_complaint.AddComplaintScreen
import com.januzanj.sipmasdes.presentation.ui.add_complaint.camera.CameraScreen
import com.januzanj.sipmasdes.presentation.ui.add_complaint.camera.CameraX
import com.januzanj.sipmasdes.presentation.ui.home.detail.ComplaintDetailScreen
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.januzanj.sipmasdes.presentation.ui.add_complaint.AddComplaintViewModel
import com.januzanj.sipmasdes.presentation.ui.add_complaint.confirm.AddComplaintConfirmScreen
import com.januzanj.sipmasdes.presentation.ui.main.SplashScreen

@Composable
fun NavigationCompose(
    context: Context,
    lifecycleOwner: LifecycleOwner,
    loginViewModel: LoginViewModel = hiltViewModel(),
    addComplaintViewModel: AddComplaintViewModel = hiltViewModel()
) {
    // Remember a SystemUiController
    val systemUiController = rememberSystemUiController()
    val useDarkIcons = MaterialTheme.colors.isLight
    val primaryVariantColor = MaterialTheme.colors.primaryVariant
    val isSystemInDarkTheme = isSystemInDarkTheme()

    val cameraX = CameraX(context, lifecycleOwner)

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
    val isLogin by loginViewModel.isLogin

    NavHost(
        navController = navController,
        startDestination = Destination.getStartDestination()
    ) {
        composable(route = Destination.Splash.route){
            SplashScreen(
                navController = navController,
                isLogin = isLogin
            )
        }
        composable(route = Destination.Login.route) {
            if (isLogin) {
                LaunchedEffect(key1 = Unit) {
                    navController.navigate(Destination.Home.route) {
                        popUpTo(Destination.Login.route) {
                            inclusive = true
                        }
                    }
                }
            } else {
                LoginScreen(loginViewModel)
            }
        }
        composable(route = Destination.Home.route) {
            if (!isLogin) {
                LaunchedEffect(key1 = Unit) {
                    navController.navigate(Destination.Login.route) {
                        popUpTo(Destination.Home.route) {
                            inclusive = true
                        }
                    }
                }
            } else {
                MainScreen(navController = navController, context = context, loginViewModel = loginViewModel)
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
        composable(route = Destination.Camera.route){
            CameraScreen(
                navController = navController,
                cameraX = cameraX,
                addComplaintViewModel
            )
        }
        composable(route = Destination.AddComplaint.route) {
            AddComplaintScreen(
                navController = navController,
                addComplaintViewModel
            )
        }
        composable(route = Destination.AddComplaintConfirm.route){
            AddComplaintConfirmScreen(
                navController = navController,
                addComplaintViewModel
            )
        }
    }

}