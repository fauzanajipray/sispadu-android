package com.devajip.sispadu.presentation.home

import android.content.res.Configuration
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.devajip.sispadu.R
import com.devajip.sispadu.presentation.theme.Orange300
import com.devajip.sispadu.presentation.theme.SispaduTheme

@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "SISPADU",
                        style = MaterialTheme.typography.h6
                    )
                },
                backgroundColor = MaterialTheme.colors.primary,
                elevation = 0.dp
            )
        },
    ) {
        Column (
            modifier = Modifier
                .background(color = MaterialTheme.colors.background)
                .verticalScroll(scrollState)
                .padding(bottom = 50.dp),
        ) {
            for (i in 0..10) {
                ItemComplaint()
            }
        }
    }

}

@Composable
fun ItemComplaint() {
    Card(
        modifier = Modifier
            .background(color = MaterialTheme.colors.surface)
            .fillMaxWidth()
            .padding(top = 8.dp)
    ) {
        Column(
            modifier = Modifier,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier
                    .padding(16.dp, 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.fauzan),
                    contentDescription = "Profile Photo",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .border(1.dp, Color.Gray, CircleShape)
                        .size(48.dp)
                        .clip(CircleShape),
                )
                Column(
                    modifier = Modifier.padding(start = 16.dp),
                    verticalArrangement = Arrangement.SpaceBetween,
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = "Username",
                        style = MaterialTheme.typography.h6
                    )
                    Text(
                        text = "#5641 | 5 jam yang lalu",
                        style = MaterialTheme.typography.body2
                    )
                }
            }
            Divider(
                color = MaterialTheme.colors.onSurface.copy(alpha = 0.12f),
                thickness = 1.dp
            )
            //Image if not empty
            Image(
                painter = painterResource(id = R.drawable.jalan_rusak),
                contentDescription = "Complaint Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),
                contentScale = ContentScale.FillWidth
            )
            Column(
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 9.dp, bottom = 20.dp),
            ) {
                Text(
                    text = "Jalan Rusak",
                    color = MaterialTheme.colors.primary,
                    style = MaterialTheme.typography.h6
                )
                Text(
                    text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Consequat libero integer morbi suscipit puru... ",
                    style = MaterialTheme.typography.body2,
                    modifier = Modifier.padding(top = 6.dp)
                )
            }
            Row(
                modifier = Modifier
                    .background(color = Orange300.copy(alpha = 0.12f))
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, top = 5.dp, bottom = 5.dp),
                horizontalArrangement = Arrangement.Start
            ) {
                Image(painter = painterResource(id = R.drawable.ic_pending) , contentDescription = "Pending")
                Text(
                    text = "Menunggu persetujuan Admin",
                    style = MaterialTheme.typography.body2,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }
    }
}

@Preview
@Composable
fun ItemComplaintPreview() {
    SispaduTheme {
        ItemComplaint()
    }
}

@Preview("HomeScreen")
@Preview("HomeScreen dark theme", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun HomeScreenPreview() {
    SispaduTheme() {
        HomeScreen()
    }
}

