package com.januzanj.sipmasdes.presentation.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.januzanj.sipmasdes.R
import com.januzanj.sipmasdes.presentation.theme.SispaduTheme

@Composable
fun NothingHere() {
    Box(
        modifier = Modifier.fillMaxSize()
    ){
        Column(modifier = Modifier.align(Alignment.Center)){
            Text(
                modifier = Modifier
                    .padding(8.dp)
                    .align(Alignment.CenterHorizontally),
                text = "¯\\_(ツ)_/¯",
                style = TextStyle(fontSize = 55.sp)
            )
            Text(
                modifier = Modifier
                    .padding(8.dp)
                    .align(Alignment.CenterHorizontally),
                text = stringResource(id = R.string.nothing_here)  ,
                style = MaterialTheme.typography.h4
            )
        }

    }
}

@Preview
@Composable
fun NothingHerePreview() {
    SispaduTheme {
        NothingHere()
    }
}