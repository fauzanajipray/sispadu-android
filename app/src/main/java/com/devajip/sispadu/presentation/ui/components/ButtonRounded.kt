package com.devajip.sispadu.presentation.ui.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ButtonRounded (
    text: String,
    enabled: Boolean = true,
    modifier: Modifier = Modifier,
    colors : ButtonColors = ButtonDefaults.buttonColors(
        backgroundColor = MaterialTheme.colors.primary,
        contentColor = MaterialTheme.colors.onPrimary,
        disabledBackgroundColor = Color.LightGray,
        disabledContentColor = Color.DarkGray
    ),
    onClick: () -> Unit,
) {
    Button(
        enabled = enabled,
        modifier = modifier,
        shape = RoundedCornerShape(6.dp),
        colors = colors,
        onClick = onClick
    ) {
        Text(text)
    }

}