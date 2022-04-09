package com.devajip.sispadu.presentation.ui.add_complaint

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.devajip.sispadu.R
import com.devajip.sispadu.presentation.theme.SispaduTheme
import com.devajip.sispadu.presentation.ui.components.Chip

@Composable
fun AddComplaintScreen(
//    navController: NavController
) {
    val scrollState = rememberLazyListState()
    val elevationSize by animateDpAsState(if (scrollState.firstVisibleItemScrollOffset == 0) 1.dp else 8.dp)

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
                backgroundColor = MaterialTheme.colors.surface,
                elevation = elevationSize
            )
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp)
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
    var isPrivate by remember { mutableStateOf(true) }
    var isAnonymous by remember { mutableStateOf(false) }

    LazyColumn(
        state = scrollState,
        modifier = modifier
    ){
        item {
            Card(
                shape = RectangleShape,
                modifier = Modifier
                    .padding(bottom = 6.dp)
                    .background(color = MaterialTheme.colors.surface)
                    .fillMaxWidth(),
                elevation = 0.dp
            ){
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        text = "Complaint Title",
                        color = MaterialTheme.colors.primary,
                        style = MaterialTheme.typography.h6
                    )
                    Text(
                        text = "Give a brief title of your complaint",
                        color = Color.LightGray,
                        style = MaterialTheme.typography.subtitle1
                    )
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
                shape = RectangleShape,
                modifier = Modifier
                    .padding(bottom = 6.dp)
                    .background(color = MaterialTheme.colors.surface)
                    .fillMaxWidth(),
                elevation = 0.dp
            ){
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        text = "Descriptions",
                        color = MaterialTheme.colors.primary,
                        style = MaterialTheme.typography.h6
                    )
                    Text(
                        text = "Explain your problems here",
                        color = Color.LightGray,
                        style = MaterialTheme.typography.subtitle1
                    )
                    OutlinedTextField(
                        value = complaintDescription,
                        onValueChange = { complaintDescription = it },
                        singleLine = true,
                        modifier = Modifier
                            .padding(top = 8.dp)
                            .fillMaxWidth()
                            .height(intrinsicSize = IntrinsicSize.Max)
                    )

                }
            }
        }
        item {
            Card(
                shape = RectangleShape,
                modifier = Modifier
                    .padding(bottom = 6.dp)
                    .background(color = MaterialTheme.colors.surface)
                    .fillMaxWidth(),
                elevation = 0.dp
            ){
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        text = "For who",
                        color = MaterialTheme.colors.primary,
                        style = MaterialTheme.typography.h6
                    )
                    Text(
                        text = "Who are you giving the complaint to",
                        color = Color.LightGray,
                        style = MaterialTheme.typography.subtitle1
                    )
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
            Column(modifier = Modifier.fillMaxWidth()) {
                Card(
                    shape = RectangleShape,
                    modifier = Modifier
                        .padding(bottom = 6.dp)
                        .background(color = MaterialTheme.colors.surface)
                        .fillMaxWidth(),
                    elevation = 0.dp
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Text(
                            text = "Want to Share?",
                            color = MaterialTheme.colors.primary,
                            style = MaterialTheme.typography.h6
                        )
                        Text(
                            text = "Share to the community or just send as a private report",
                            color = Color.LightGray,
                            style = MaterialTheme.typography.subtitle1
                        )
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            Chip(
                                "Private", isPrivate,
                                Modifier
                                    .padding(start = 0.dp, top = 8.dp, end = 8.dp, bottom = 8.dp)
                                    .clickable {
                                        isPrivate = !isPrivate
                                        if (isAnonymous) {
                                            isAnonymous = false
                                        }
                                    }
                            )
                            Chip(
                                "Community", !isPrivate,
                                Modifier
                                    .padding(start = 0.dp, top = 8.dp, end = 8.dp, bottom = 8.dp)
                                    .clickable {
                                        isPrivate = !isPrivate
                                        if (isAnonymous) {
                                            isAnonymous = false
                                        }
                                    }
                            )
                        }
                    }
                }
            }
            AnimatedVisibility(
                visible = !isPrivate,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                LaunchedEffect(key1 = Unit) {
                    if (isPrivate) {
                        scrollState.scrollToItem(3)
                    } else {
                        scrollState.scrollToItem(
                            3,
                            100
                        )
                    }
                }
                Card(
                    shape = RectangleShape,
                    modifier = Modifier
                        .padding(bottom = 6.dp)
                        .background(color = MaterialTheme.colors.surface)
                        .fillMaxWidth(),
                    elevation = 0.dp
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Text(
                            text = "Anonymous",
                            color = MaterialTheme.colors.primary,
                            style = MaterialTheme.typography.h6
                        )
                        Text(
                            text = "Hide your identity from the public",
                            color = Color.LightGray,
                            style = MaterialTheme.typography.subtitle1
                        )
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                        ){
                            Chip(
                                "Hide", isAnonymous,
                                Modifier
                                    .padding(start = 0.dp, top = 8.dp, end = 8.dp, bottom = 8.dp)
                                    .clickable {
                                        isAnonymous = !isAnonymous
                                    }
                            )
                            Chip(
                                "Hide", !isAnonymous,
                                Modifier
                                    .padding(start = 0.dp, top = 8.dp, end = 8.dp, bottom = 8.dp)
                                    .clickable {
                                        isAnonymous = !isAnonymous
                                    }
                            )
                        }
                    }
                }
            }
        }
        item {
            Spacer(modifier = Modifier.height(1.dp).background(color = Color.LightGray))
            Card(
                shape = RectangleShape,
                modifier = Modifier
                    .background(color = MaterialTheme.colors.surface)
                    .fillMaxWidth(),
                elevation = 6.dp
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_baseline_image_24),
                        contentDescription = "Add Image",
                        modifier = Modifier
                            .size(32.dp)
                            .clickable { /* TODO Function Add Image */ },
                        colorFilter = ColorFilter.tint(MaterialTheme.colors.primary)
                    )
                    Button(onClick = { /*TODO Post Complaint*/ }) {
                        Text(text = "Submit")
                    }
                }
            }
        }
    }

}


@Preview("AddComplaintScreen")
@Preview("AddComplaintScreen (dark)", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun AddComplaintScreenPreview(){
    SispaduTheme {
        AddComplaintScreen()
    }
}
