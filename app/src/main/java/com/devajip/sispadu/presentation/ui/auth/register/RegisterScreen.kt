package com.devajip.sispadu.presentation.ui.auth.register

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.devajip.sispadu.presentation.ui.auth.login.LoginScreen
import com.devajip.sispadu.presentation.theme.SispaduTheme

@Composable
fun RegisterScreen(
    viewModel: RegisterViewModel = hiltViewModel<RegisterViewModel>()
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
            Text(
                text = "Register",
                style = MaterialTheme.typography.h4,
                color = Color.White,
                modifier = Modifier
                    .weight(1f)
                    .padding(16.dp)
                    .align(Alignment.CenterHorizontally)
            )
            Card(
                Modifier.weight(2f),
                shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp),
            ) {
                Column(
                    Modifier
                        .fillMaxSize()
                        .padding(32.dp)
                ) {

                }
            }
        }
    }
}

@Preview
@Composable
fun DefaultPreview() {
    SispaduTheme {
        LoginScreen()
    }
}

// dark theme
@Preview
@Composable
fun DarkPreview() {
    SispaduTheme(darkTheme = true) {
        LoginScreen()
    }
}