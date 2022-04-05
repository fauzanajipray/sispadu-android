package com.devajip.sispadu.presentation.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.devajip.sispadu.R
import com.devajip.sispadu.common.Constant
import com.devajip.sispadu.common.Constant.BASE_URL
import com.devajip.sispadu.common.getDateForHuman
import com.devajip.sispadu.common.getStatusComplaint
import com.devajip.sispadu.common.loadImage
import com.devajip.sispadu.domain.model.Complaint
import com.devajip.sispadu.presentation.ui.components.ErrorItem
import com.devajip.sispadu.presentation.ui.components.LoadingItem
import com.devajip.sispadu.presentation.ui.components.LoadingView
import com.devajip.sispadu.presentation.navigation.Destination
import kotlinx.coroutines.flow.Flow


@Composable
fun ComplaintList(
    complaints: Flow<PagingData<Complaint>>,
    navController: NavController,
    complaintViewModel: ComplaintViewModel,
    scrollState: LazyListState
) {
    val lazyComplaintItems = complaints.collectAsLazyPagingItems()

    LazyColumn(
        modifier = Modifier
            .padding(bottom = 55.dp,
                top = if (scrollState.firstVisibleItemScrollOffset == 0) 6.dp else 0.dp
            ),
        state = scrollState,
    ) {
        items(lazyComplaintItems) { complaint ->
            if (complaint != null) {
                ComplaintItem(
                    complaint = complaint,
                    onClick = {
                        complaintViewModel.getComplaintDetail(it)
                        navController.navigate(
                            Destination.ComplaintDetail.route.plus("/$it")
                        )
                    }
                )
            }
        }

        lazyComplaintItems.apply {
            when {
                loadState.refresh is LoadState.Loading -> {
                    item { LoadingView(modifier = Modifier.fillParentMaxSize()) }
                }
                loadState.append is LoadState.Loading -> {
                    item { LoadingItem() }
                }
                loadState.refresh is LoadState.Error -> {
                    val e = lazyComplaintItems.loadState.refresh as LoadState.Error
                    item {
                        ErrorItem(
                            message = e.error.localizedMessage!!,
                            modifier = Modifier.fillParentMaxSize(),
                            onClickRetry = { retry() }
                        )
                    }
                }
                loadState.append is LoadState.Error -> {
                    val e = lazyComplaintItems.loadState.append as LoadState.Error
                    item {
                        ErrorItem(
                            message = e.error.localizedMessage!!,
                            onClickRetry = { retry() }
                        )
                    }
                }

            }
        }
    }
}

@Composable
fun ComplaintItem(
    complaint: Complaint,
    onClick: (Int) -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp)
            .background(color = MaterialTheme.colors.surface)
            .clickable { onClick(complaint.id) },
        elevation = 0.dp
    ) {
        val statusComplaint  = if (complaint.status == Constant.STATUS_PENDING) {
            getStatusComplaint(status = complaint.status)
        } else {
            getStatusComplaint(status = complaint.status, toName = complaint.positionName)
        }

        Column(
            modifier = Modifier,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            UserProfileRowComplaint(
                image = complaint.userImage ?: "${BASE_URL}images/undraw_profile.svg",
                name = complaint.userName,
                id = complaint.id,
                date = complaint.createdAt,
            )
            Divider(
                color = MaterialTheme.colors.onSurface.copy(alpha = 0.12f),
                thickness = 1.dp
            )
            if (complaint.image.isNotEmpty()) {
                // TODO: Change using AsyncImage
                val image = loadImage(
                    url = complaint.image,
                    defaultImage = R.drawable.ic_baseline_image_24
                ).value
                image?.let {
                    Image(
                        bitmap = it.asImageBitmap(),
                        contentDescription = "Complaint Image",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(150.dp),
                        contentScale = ContentScale.FillWidth
                    )
                }
            }
            Column(
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 9.dp, bottom = 20.dp),
            ) {
                Text(
                    text = complaint.title,
                    color = MaterialTheme.colors.primary,
                    style = MaterialTheme.typography.h6
                )
                Text(
                    text = complaint.description,
                    style = MaterialTheme.typography.body2,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(top = 6.dp),
                )
            }
            Row(
                modifier = Modifier
                    .background(color = statusComplaint.color.copy(alpha = 0.12f))
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, top = 5.dp, bottom = 5.dp),
                horizontalArrangement = Arrangement.Start
            ) {
                Image(painter = painterResource(id = statusComplaint.icon) , contentDescription = "Pending")
                Text(
                    text = statusComplaint.message,
                    style = MaterialTheme.typography.body2,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }
    }
}

@Composable
fun UserProfileRowComplaint(
    image: String,
    name: String,
    id: Int,
    date: String,
){
    val dateForHuman = getDateForHuman(date = date)
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.surface)
            .padding(16.dp, 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(image)
                .crossfade(true)
                .build(),
            placeholder = painterResource(R.drawable.ic_baseline_image_24),
            contentDescription = stringResource(R.string.profile_photo),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .clip(CircleShape)
                .border(1.dp, Color.Gray, CircleShape)
                .size(48.dp)
        )
        Column(
            modifier = Modifier.padding(start = 16.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = name,
                style = MaterialTheme.typography.h6
            )
            Row(
                modifier = Modifier.padding(top = 4.dp),
                horizontalArrangement = Arrangement.Start
            ) {
                Text(
                    text = "#$id",
                    style = MaterialTheme.typography.body2,
                    modifier = Modifier.padding(start = 8.dp , end = 6.dp)
                )
                Text(text = "|", style = MaterialTheme.typography.body2)
                Text(
                    text = dateForHuman,
                    style = MaterialTheme.typography.body2,
                    modifier = Modifier.padding(start = 6.dp)
                )
            }
        }
    }
}