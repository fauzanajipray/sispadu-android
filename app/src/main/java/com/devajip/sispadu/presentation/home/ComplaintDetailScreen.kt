package com.devajip.sispadu.presentation.home

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.R
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavController
import com.devajip.sispadu.common.Constant
import com.devajip.sispadu.common.Constant.BASE_URL
import com.devajip.sispadu.common.loadImage
import com.devajip.sispadu.domain.model.ComplaintDetail
import com.devajip.sispadu.presentation.components.ErrorItem
import com.devajip.sispadu.presentation.components.ErrorView
import com.devajip.sispadu.presentation.components.LoadingView
import okhttp3.MultipartBody
import retrofit2.http.Body

@Composable
fun ComplaintDetailScreen(
    complaintViewModel: ComplaintViewModel = hiltViewModel(),
    navController: NavController,
    complaintId: Int
) {
    LaunchedEffect(key1 = Unit) {
        complaintViewModel.getComplaintDetail(complaintId)
    }

    val state = complaintViewModel.stateDetail.value
    val context = LocalContext.current
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Complaint Detail") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                backgroundColor = MaterialTheme.colors.surface
            )
        }
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            if (state.isLoading) {
                LoadingView(
                    modifier = Modifier.fillMaxSize().align(Alignment.Center)
                )
            }
            else if(!state.isLoading) {
                state.complaint?.let { it1 -> ComplaintDetailContents(complaint = it1) }
            }
            else {
                ErrorView(message = "Error Occurred", modifier = Modifier.fillMaxSize().align(Alignment.Center))
                if (state.error != null) {
                    LaunchedEffect(key1 = Unit){
                        Toast.makeText(context, state.error.toString(), Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}

@Composable
fun ComplaintDetailContents(
    complaint: ComplaintDetail
) {
    Column(modifier = Modifier.fillMaxSize()) {
        if (!complaint.image.isNullOrEmpty()){
            val image = loadImage(
                url = complaint.image,
                defaultImage = com.devajip.sispadu.R.drawable.ic_baseline_image_24
            ).value
            image?.let {
                Image(
                    bitmap = it.asImageBitmap(),
                    contentDescription = "Complaint Image",
                    modifier = Modifier
                        .fillMaxWidth(),
                    contentScale = ContentScale.FillWidth
                )
            }
        }
        UserProfileRowComplaint(
            image = complaint.image ?: "${BASE_URL}images/undraw_profile.svg",
            name = complaint.userName,
            id = complaint.id,
            date = complaint.createdAt,
        )
//        Divider(
//            color = MaterialTheme.colors.onSurface.copy(alpha = 0.12f),
//            thickness = 1.dp
//        )
        Modifier.fillMaxWidth().background(MaterialTheme.colors.surface).padding(16.dp)
    }
}

@Composable
fun BodyComplaintDetail(
    title: String,
    body: String,
    toName: String,
    status: String,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = title,
            style = MaterialTheme.typography.h6,
            color = MaterialTheme.colors.primary
        )
        Text(
            text = body,
            style = MaterialTheme.typography.body1,
            color = MaterialTheme.colors.onSurface,
            modifier = Modifier.padding(top = 16.dp)
        )
        
    }
}

@Preview
@Composable
fun BodyComplaintDetailPreview() {
    BodyComplaintDetail(
        title = "Title",
        body = "Lorem1234 ipsum dolor sit amet. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
        toName = "Kepala Desa",
        status = "Diteruskan",
        modifier = Modifier.fillMaxWidth().background(MaterialTheme.colors.surface).padding(16.dp)
    )
}
