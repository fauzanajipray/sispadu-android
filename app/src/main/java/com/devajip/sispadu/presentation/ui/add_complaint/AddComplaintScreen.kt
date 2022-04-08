package com.devajip.sispadu.presentation.ui.add_complaint

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.devajip.sispadu.presentation.theme.SispaduTheme

@Composable
fun AddComplaintScreen(
//    navController: NavController
) {
    val scrollState = rememberLazyListState()
    val paddingTop by animateDpAsState( if (scrollState.firstVisibleItemScrollOffset == 0) 6.dp else 0.dp)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add Complaint") },
                navigationIcon = {
                    IconButton(onClick = {
//                        navController.popBackStack()
                    }) {
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                backgroundColor = MaterialTheme.colors.surface
            )
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = paddingTop)
        ) {
            AddComplaintForm(modifier = Modifier, scrollState)
        }
    }
}

@Composable
fun AddComplaintForm(
    modifier: Modifier,
    scrollState: LazyListState
) {
    var complaintTitle by remember { mutableStateOf("") }
    var complaintDescription by remember { mutableStateOf("") }

    LazyColumn(
        state = scrollState,
        modifier = modifier
            .padding(bottom = 30.dp)
    ){
        item {
            Card(
                modifier = Modifier
                    .padding(bottom = 6.dp)
                    .background(color = MaterialTheme.colors.surface)
                    .fillMaxWidth()
            ){
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        text = "Judul Laporan",
                        color = MaterialTheme.colors.primary,
                        style = MaterialTheme.typography.h6
                    )
                    //Subtitle
                    Text(
                        text = "Berikan judul yang singkat dari laporanmu",
                        color = Color.LightGray,
                        style = MaterialTheme.typography.subtitle1
                    )
                    //TextInput
                    OutlinedTextField(
                        value = complaintTitle,
                        onValueChange = { complaintTitle = it },
                        singleLine = true,
                        modifier = Modifier
                            .padding(top = 8.dp)
                            .fillMaxWidth()
                    )
                }
            }
        }
        item {
            Card(
                modifier = Modifier
                    .padding(bottom = 6.dp)
                    .background(color = MaterialTheme.colors.surface)
                    .fillMaxWidth()
            ){
                Column(
                    modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                ) {
                    Text(
                        text = "Judul Laporan",
                        color = MaterialTheme.colors.primary,
                        style = MaterialTheme.typography.h6
                    )
                    //Subtitle
                    Text(
                        text = "Berikan judul yang singkat dari laporanmu",
                        color = Color.LightGray,
                        style = MaterialTheme.typography.subtitle1
                    )
                    //Input Body Complaint
                    OutlinedTextField(
                        value = complaintDescription,
                        onValueChange = { complaintDescription = it },
                        modifier = Modifier
                            .padding(top = 8.dp)
                            .fillMaxWidth()
                            .height(intrinsicSize = IntrinsicSize.Max)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun AddComplaintScreenPreview(){
    SispaduTheme {
        AddComplaintScreen()
    }
}