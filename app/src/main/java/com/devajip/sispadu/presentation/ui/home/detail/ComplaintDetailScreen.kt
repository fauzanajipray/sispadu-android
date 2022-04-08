package com.devajip.sispadu.presentation.ui.home.detail

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.devajip.sispadu.R
import com.devajip.sispadu.common.*
import com.devajip.sispadu.common.Constant.BASE_URL
import com.devajip.sispadu.domain.model.Comment
import com.devajip.sispadu.domain.model.ComplaintDetail
import com.devajip.sispadu.presentation.ui.components.ErrorView
import com.devajip.sispadu.presentation.ui.components.LoadingView
import com.devajip.sispadu.presentation.ui.home.ComplaintViewModel
import com.devajip.sispadu.presentation.ui.home.UserProfileRowComplaint

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
    var scrolledY = 0f
    var previousOffset = 0

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
            item {
                Column(
                    modifier = Modifier
                        .padding(top = 6.dp, bottom = 2.dp)
                        .fillMaxWidth()
                        .background(MaterialTheme.colors.surface)
                        .padding(16.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.comments),
                        style = MaterialTheme.typography.h6,
                        color = MaterialTheme.colors.onSurface.copy(alpha = 0.87f),
                        modifier = Modifier
                    )
                }
            }

            complaint.comments.forEach { comment ->
                item{
                    comment?.let { it ->
                        if (it.id == complaint.comments.last()?.id) {
                            CommentRow(
                                comment = it,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(MaterialTheme.colors.surface)
                                    .padding(start = 16.dp, end = 16.dp, top = 6.dp, bottom = 30.dp)
                            )
                        }
                        else if (it.id == complaint.comments.first()?.id) {
                            CommentRow(
                                comment = it,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(MaterialTheme.colors.surface)
                                    .padding(start = 16.dp, end = 16.dp, top = 10.dp, bottom = 4.dp)
                            )
                        }
                        else {
                            CommentRow(
                                comment = it,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(MaterialTheme.colors.surface)
                                    .padding(start = 16.dp, end = 16.dp, top = 6.dp, bottom = 4.dp)
                            )
                        }
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
    val statusComplaint = getStatusComplaint(status = comment.status)

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Card(
            shape = RoundedCornerShape(8.dp),
            backgroundColor = statusComplaint.color.copy(alpha = 0.12f),
            border = BorderStroke(0.dp, Color.Transparent),
            elevation = 0.dp,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .padding(start = 10.dp, end = 10.dp, top = 10.dp, bottom = 10.dp)
                    .fillMaxWidth()
            ) {
                val image = painterResource(id = R.drawable.fauzan)
                Image(
                    painter = image,
                    contentDescription = "",
                    modifier = Modifier
                        .size(40.dp, 40.dp)
                        .clip(CircleShape)

                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 11.dp)
                ) {
                    Text(
                        text = comment.fromRole,
                        style = MaterialTheme.typography.h6,
                        color = MaterialTheme.colors.onSurface,
                        modifier = Modifier
                            .padding(bottom = 4.dp)
                    )
                    Text(
                        text = comment.body,
                        style = MaterialTheme.typography.body2,
                        color = MaterialTheme.colors.onSurface,
                        modifier = Modifier
                            .padding(bottom = 4.dp)
                    )
                    Text(
                        text = getDateForHuman(date = comment.createdAt),
                        style = MaterialTheme.typography.body2,
                        color = MaterialTheme.colors.onSurface,
                        modifier = Modifier
                            .padding(bottom = 4.dp)
                    )
                }
            }
        }

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
                colorFilter = ColorFilter.tint(Color.LightGray),
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

//@Preview()
//@Composable
//fun BodyComplaintDetailPreview() {
//    BodyComplaintDetail(
//        title = "Title",
//        desc = "Lorem1234 ipsum dolor sit amet. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
//        toName = "Kepala Desa",
//        status = Constant.STATUS_ACCEPTED,
//        modifier = Modifier
//            .fillMaxWidth()
//            .background(MaterialTheme.colors.surface)
//            .padding(16.dp)
//    )
//}

@Preview()
@Composable
fun CommentPreview() {
    val commentDummy = Comment(
        id = 1,
        userId = 1,
        complaintId = 1,
        body = "Laporan didisposisi kepada Kepala Desa",
        status = Constant.STATUS_FORWARD,
        fromRole = "Admin",
        createdAt = "2022-04-05T10:06:57.000000Z",
        updatedAt = "2022-04-05T10:06:57.000000Z"
    )
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.DarkGray)
    ){
        item{
            Column(
                modifier = Modifier
                    .padding(top = 6.dp, bottom = 1.dp)
                    .fillMaxWidth()
                    .background(MaterialTheme.colors.surface)
                    .padding(16.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.comments),
                    style = MaterialTheme.typography.h6,
                    color = MaterialTheme.colors.onSurface.copy(alpha = 0.87f),
                    modifier = Modifier
                )
            }
        }
        item{
            CommentRow(
                comment = commentDummy,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colors.surface)
                    .padding(start = 16.dp, end = 16.dp, top = 6.dp, bottom = 5.dp)
            )
        }
        item{
            CommentRow(
                comment = commentDummy.copy(
                    status = Constant.STATUS_REJECTED,
                    fromRole = "Kepala Desa",
                    body = "Mohon maaf laporan ini ditolak"
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colors.surface)
                    .padding(start = 16.dp, end = 16.dp, top = 6.dp, bottom = 30.dp)
            )
        }
    }
}