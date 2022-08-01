package com.januzanj.sipmasdes.presentation.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.januzanj.sipmasdes.presentation.theme.Blue700
import com.januzanj.sipmasdes.presentation.theme.Orange300

@Composable
fun getColorStatusAlpha(status: String): Color {
    return when (status) {
        "Menunggu" -> Orange300.copy(alpha = 0.12f)
        "Diteruskan" -> Blue700.copy(alpha = 0.12f)
        "Selesai" -> Color.Green.copy(alpha = 0.12f)
        "Batal" -> Color.Red.copy(alpha = 0.12f)
        else -> Color.White
    }
}