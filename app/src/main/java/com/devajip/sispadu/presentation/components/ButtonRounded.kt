package com.devajip.sispadu.presentation.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ButtonRounded (
    text: String,
    enabled: Boolean = true,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    color: Color = MaterialTheme.colors.primary,
) {
    Button(
        enabled = enabled,
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
//        colors = bgColor,
        onClick = onClick
    ) {
        Text(text)
    }
}