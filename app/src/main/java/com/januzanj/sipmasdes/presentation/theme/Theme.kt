package com.januzanj.sipmasdes.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary = Purple200,
    primaryVariant = Purple700,
    secondary = Teal200
)

private val LightColorPalette = lightColors(
    primary = Blue700,
    primaryVariant = Grey100,
    secondary = Blue700,

    background = Grey50,
    surface = Color.White,
    onPrimary = White,
    onSecondary = White,
    onBackground = Color.Black,
    onSurface = Color.DarkGray,
    onError = Color.Red,
    error = Color.Red

)

@Composable
fun SispaduTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}