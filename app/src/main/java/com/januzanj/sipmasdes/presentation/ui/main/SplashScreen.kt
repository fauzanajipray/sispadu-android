package com.januzanj.sipmasdes.presentation.ui.main

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.januzanj.sipmasdes.presentation.theme.SispaduTheme
import com.januzanj.sipmasdes.R
import com.januzanj.sipmasdes.presentation.navigation.Destination
import com.januzanj.sipmasdes.presentation.ui.add_complaint.AddComplaintState
import com.januzanj.sipmasdes.presentation.ui.add_complaint.AddComplaintViewModel
import com.januzanj.sipmasdes.presentation.ui.components.ErrorItem
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    navController: NavHostController,
    isLogin: Boolean
) {
    var startAnimation by remember { mutableStateOf(false) }
    val alphaAnimation = animateFloatAsState(
        targetValue = if(startAnimation) 1f else 0f,
        animationSpec = tween(durationMillis = 3000)
    )
    LaunchedEffect(key1 = true){
        startAnimation = true
        delay(4000)
        if (!isLogin) {
            navController.navigate(Destination.Login.route)
        } else {
            navController.navigate(Destination.Home.route)
        }
    }

    Splash(alpha = alphaAnimation.value)
}

@Composable
fun Splash(
    alpha: Float
) {
    val isDarkTheme = isSystemInDarkTheme()
    val backgroundColor = if (isDarkTheme) MaterialTheme.colors.surface else MaterialTheme.colors.background
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        backgroundColor = backgroundColor,
    ) {
        Box(modifier = Modifier.fillMaxSize()){
            Image(
                modifier = Modifier
                    .size(100.dp)
                    .alpha(alpha)
                    .align(Alignment.Center),
                painter = painterResource(R.mipmap.ic_launcher),
                contentDescription = "Logo",
            )
        }
    }
}

@Preview
@Composable
fun SplashScreenPreview() {
    SispaduTheme {
        //SplashScreen(navController = null, isLogin = false, addComplaintViewModel = null)
    }
}