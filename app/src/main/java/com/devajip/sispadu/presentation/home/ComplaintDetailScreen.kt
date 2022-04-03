package com.devajip.sispadu.presentation.home

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.devajip.sispadu.R
import com.devajip.sispadu.common.Constant
import com.devajip.sispadu.common.Constant.BASE_URL
import com.devajip.sispadu.common.getStatusComplaint
import com.devajip.sispadu.common.loadImage
import com.devajip.sispadu.domain.model.Comment
import com.devajip.sispadu.domain.model.ComplaintDetail
import com.devajip.sispadu.presentation.components.ErrorView
import com.devajip.sispadu.presentation.components.LoadingView
import com.devajip.sispadu.presentation.theme.Blue200

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
                    modifier = Modifier
                        .fillMaxSize()
                        .align(Alignment.Center)
                )
            }
            else if(!state.isLoading) {
                state.complaint?.let { it1 -> ComplaintDetailContents(complaint = it1) }
            }
            else {
                ErrorView(message = "Error Occurred", modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.Center))
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
    val scrollState = rememberLazyListState()

    //scroll column
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        state = scrollState,
    ) {
        if (!complaint.image.isNullOrEmpty()) {
            item {
                val image = loadImage(
                    url = complaint.image,
                    defaultImage = R.drawable.ic_baseline_image_24
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
        }
        item {
            UserProfileRowComplaint(
                image = complaint.image ?: "${BASE_URL}images/undraw_profile.svg",
                name = complaint.userName,
                id = complaint.id,
                date = complaint.createdAt,
            )
        }
        item {
            BodyComplaintDetail(
                title = complaint.title,
                desc = complaint.description,
                toName = complaint.positionName,
                status = complaint.status,
                modifier = Modifier
                    .padding(top = 6.dp)
                    .fillMaxWidth()
                    .background(MaterialTheme.colors.surface)
                    .padding(16.dp)
            )
        }
        if (!complaint.comments.isNullOrEmpty()) {
            item() {
                Column(
                    modifier = Modifier
                        .padding(top = 6.dp)
                        .fillMaxWidth()
                        .background(MaterialTheme.colors.onSurface)
                        .padding(16.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.comments),
                        style = MaterialTheme.typography.h6,
                        color = Color.LightGray,
                        modifier = Modifier
                    )
                }
            }

            complaint.comments.forEach { comment ->
                item{
                    comment?.let { it ->
                        CommentRow(
                            comment = it,
                            modifier = Modifier
                                .fillMaxWidth()
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CommentRow(
    comment: Comment,
    modifier: Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = comment.body,
            style = MaterialTheme.typography.h6,
            modifier = Modifier
                .padding(top = 16.dp)
                .fillMaxWidth()
        )
    }
}

@Composable
fun BodyComplaintDetail(
    title: String,
    desc: String,
    toName: String,
    status: String,
    modifier: Modifier = Modifier
) {
    val statusComplaint  = if (status == Constant.STATUS_PENDING) {
        getStatusComplaint(status = status)
    } else {
        getStatusComplaint(status = status, toName = toName)
    }

    Column(modifier = modifier) {
        Text(
            text = title,
            style = MaterialTheme.typography.h6,
            color = MaterialTheme.colors.primary
        )
        Text(
            text = desc,
            style = MaterialTheme.typography.body1,
            color = MaterialTheme.colors.onSurface,
            modifier = Modifier.padding(top = 16.dp)
        )
        Row(modifier = Modifier.padding(top = 16.dp)) {
            Image(
                painter = painterResource(id = R.drawable.ic_baseline_supervised_user_circle_24),
                contentDescription = "To",
                colorFilter = ColorFilter.tint(Blue200),
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = toName,
                style = MaterialTheme.typography.body1,
                color = MaterialTheme.colors.onSurface
            )
        }
        Row(modifier = Modifier.padding(top = 16.dp)) {
            Image(
                painter = painterResource(id = statusComplaint.icon),
                contentDescription = "Status",
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = status,
                style = MaterialTheme.typography.body1,
                color = MaterialTheme.colors.onSurface
            )
        }
    }
}

@Preview()
@Composable
fun BodyComplaintDetailPreview() {
    BodyComplaintDetail(
        title = "Title",
        desc = "Lorem1234 ipsum dolor sit amet. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
        toName = "Kepala Desa",
        status = "Diteruskan",
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.surface)
            .padding(16.dp)
    )
}
