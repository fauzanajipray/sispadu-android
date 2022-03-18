package com.devajip.sispadu.presentation.home

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
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
import com.devajip.sispadu.common.Constant.BASE_URL
import com.devajip.sispadu.common.loadImage
import com.devajip.sispadu.domain.model.Complaint
import com.devajip.sispadu.presentation.components.ErrorItem
import com.devajip.sispadu.presentation.components.LoadingItem
import com.devajip.sispadu.presentation.components.LoadingView
import com.devajip.sispadu.presentation.components.rememberForeverLazyListState
import com.devajip.sispadu.presentation.navigation.Destination
import com.devajip.sispadu.presentation.theme.Orange300
import kotlinx.coroutines.flow.Flow


@Composable
fun ComplaintList(
    complaints: Flow<PagingData<Complaint>>,
    navController: NavController,
    scrollState: LazyListState,
    complaintViewModel: ComplaintViewModel
) {
    val lazyComplaintItems = complaints.collectAsLazyPagingItems()
    val scrollState = rememberForeverLazyListState(key = "ComplaintList")

    LazyColumn(
        modifier = Modifier
            .padding(bottom = 55.dp, top = 5.dp),
        state = scrollState,
    ) {
        items(lazyComplaintItems) { complaint ->
            ComplaintItem(
                complaint = complaint!!,
                onClick = {
                    complaintViewModel.getComplaintDetail(it)
                    navController.navigate(
                        Destination.ComplaintDetail.route.plus("/$it")
                    )
                }
            )
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
            if (!complaint.image.isNullOrEmpty()) {
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

@Composable
fun UserProfileRowComplaint(
    image: String,
    name: String,
    id: Int,
    date: String,
){
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
            Text(
                text = "#$id",
                style = MaterialTheme.typography.body2
            )
        }
    }
}