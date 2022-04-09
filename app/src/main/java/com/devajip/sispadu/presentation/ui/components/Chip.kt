package com.devajip.sispadu.presentation.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun Chip(
    text : String,
    isActive: Boolean,
    modifier: Modifier = Modifier,
){
    val colorActive = if (isActive)  MaterialTheme.colors.primary else Color.LightGray
    val colorText = if (isActive) Color.White else Color.Black
    Card(
        shape = RoundedCornerShape(16.dp),
        backgroundColor = colorActive,
        modifier = modifier
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Text(
                text = text,
                color = colorText
            )
        }
    }
}