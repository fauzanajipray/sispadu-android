package com.devajip.sispadu.presentation.auth.login

import android.util.Patterns
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.devajip.sispadu.R
import com.devajip.sispadu.data.source.remote.response.LoginRequest
import com.devajip.sispadu.presentation.components.ButtonRounded
import com.devajip.sispadu.presentation.theme.SispaduTheme

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel(),
) {
    val state = viewModel.state.value
    val context = LocalContext.current
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isPasswordVisible by remember { mutableStateOf(false) }
    val isFormValid by derivedStateOf {
        email.isNotEmpty() && password.isNotEmpty() && password.length >= 7
                && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        backgroundColor = MaterialTheme.colors.background,
    ) {
        Column(
            Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_logo_herbify),
                contentDescription = "App Logo",
                modifier = Modifier
                    .weight(1f)
                    .size(200.dp),
                colorFilter = ColorFilter.tint(MaterialTheme.colors.onBackground)
            )
            Card(
                Modifier.weight(2f),
                shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp),
                backgroundColor = MaterialTheme.colors.surface
            ) {
                Column(
                    Modifier
                        .fillMaxSize()
                        .padding(32.dp)
                ) {
                    Text(
                        text = "Login",
                        fontWeight = FontWeight.Bold,
                        fontSize = 32.sp,
                    )
                    Column(
                        Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Spacer(modifier = Modifier.weight(1f))
                        OutlinedTextField(
                            modifier = Modifier.fillMaxWidth(),
                            label = { Text("Email") },
                            value = email,
                            onValueChange = { email = it },
                            singleLine = true,
                            trailingIcon = {
                                if (email.isNotEmpty()) {
                                    IconButton(onClick = { email = "" }) {
                                        Icon(imageVector = Icons.Filled.Clear, contentDescription = "")
                                    }
                                }
                            }
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedTextField(
                            modifier = Modifier.fillMaxWidth(),
                            value = password,
                            onValueChange = { password = it },
                            label = { Text(text = "Password") },
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Done),
                            visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                            trailingIcon = {
                                IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                                    Icon(
                                        imageVector = if (isPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                        contentDescription = "Password Toggle"
                                    )
                                }
                            }
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        ButtonRounded(
                            text = "Login",
                            enabled = isFormValid,
                            modifier = Modifier.fillMaxWidth(),
                        ) {
                            if (isFormValid) {
                                viewModel.login(LoginRequest(email, password))
                            }
                        }
                        if (state.isLoading) {
                            Spacer(modifier = Modifier.weight(1f))
                            CircularProgressIndicator(
                                modifier = Modifier.align(Alignment.CenterHorizontally)
                            )
                        }
                        if (state.error != null) {
                            LaunchedEffect(key1 = Unit){
                                Toast.makeText(context, state.error, Toast.LENGTH_SHORT).show()
                            }
                        }
                        Spacer(modifier = Modifier.weight(1f))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            TextButton(onClick = {}) {
                                Text(text = "Sign Up")
                            }
                            TextButton(onClick = {}) {
                                Text(text = "Forgot Password?", color = Color.Gray)
                            }
                        }
                    }
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
