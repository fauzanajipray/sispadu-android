package com.devajip.sispadu.presentation.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.devajip.sispadu.presentation.theme.SispaduTheme

@Composable
fun HomeScreen(
    viewModel: LogoutViewModel = hiltViewModel()
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        backgroundColor = MaterialTheme.colors.primary,
    ) {
        Column(
            Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
//            ButtonRounded(text = "Logout"){
//
//            }
        }
    }
}

@Preview
@Composable
fun DefaultPreview() {
    SispaduTheme {
        HomeScreen()
    }
}

// dark theme
@Preview
@Composable
fun DarkPreview() {
    SispaduTheme(darkTheme = true) {
        HomeScreen()
    }
}