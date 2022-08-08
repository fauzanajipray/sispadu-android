package com.januzanj.sipmasdes.presentation.ui.add_complaint.confirm

import android.content.res.Configuration
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.januzanj.sipmasdes.presentation.theme.SispaduTheme
import com.januzanj.sipmasdes.presentation.ui.add_complaint.AddComplaintViewModel

@Composable
fun AddComplaintConfirmScreen(
    navController: NavHostController,
    addComplaintViewModel: AddComplaintViewModel
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
        },
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = "Complaint added successfully",
                style = MaterialTheme.typography.h6,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            Text(
                text = "Your complaint will be reviewed by the SIPMAS DES team and you will be notified via email when it is published.",
                style = MaterialTheme.typography.subtitle1,
                modifier = Modifier.padding(bottom = 16.dp),
                textAlign = TextAlign.Center
            )
            Text(
                text = "Thank you for your contribution to the SIPMAS DES community.",
                style = MaterialTheme.typography.subtitle1,
                modifier = Modifier.padding(bottom = 16.dp),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview("AddComplaintScreen")
@Preview("AddComplaintScreen (dark)", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun AddComplaintScreenPreview(){
    SispaduTheme {
//        AddComplaintConfirmScreen(navController, addComplaintViewModel)
    }
}