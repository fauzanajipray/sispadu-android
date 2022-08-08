package com.januzanj.sipmasdes.presentation.ui.add_complaint

import android.Manifest
import android.content.res.Configuration
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.*
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.DrawerDefaults.scrimColor
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.januzanj.sipmasdes.R
import com.januzanj.sipmasdes.presentation.theme.SispaduTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.januzanj.sipmasdes.domain.model.Position
import com.januzanj.sipmasdes.presentation.navigation.Destination
import com.januzanj.sipmasdes.presentation.ui.components.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class, ExperimentalPermissionsApi::class)
@Composable
fun AddComplaintScreen(
    navController: NavController,
    viewModel: AddComplaintViewModel
) {
    val scrollState = rememberLazyListState()
    val elevationSize by animateDpAsState(if (scrollState.firstVisibleItemScrollOffset == 0) 1.dp else 8.dp)
    val modalBottomSheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val scope = rememberCoroutineScope()
    scope.launch {
        modalBottomSheetState.hide()
    }
    val permissionsState = rememberMultiplePermissionsState(
        permissions = listOf(
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
        )
    )

    val loadingPositionState by viewModel.getPositionLoadingState
    val viewLoadingState by viewModel.getPositionState

    val launcherGallery =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) {
            if (it != null) {
                viewModel.setImageUri(it)
                scope.launch {
                    modalBottomSheetState.hide()
                }
            }
            Log.d("AddComplaintScreen", "selectGallery: $it")
        }
    ModalDrawer(drawerContent = {
        BottomSheetItem(
            icon = R.drawable.ic_baseline_camera_alt_24,
            title = "Take photo",
            onItemClick = {

            })
        BottomSheetItem(
            icon = R.drawable.ic_baseline_folder_open_24,
            title = "Insert from gallery",
            onItemClick = {

            })
    }) {
        ModalBottomSheetLayout(
            sheetContent = { BottomSheetContent(navController,permissionsState,launcherGallery) },
            sheetState = modalBottomSheetState,
            sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
            scrimColor = scrimColor,
        ) {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = { Text("Add Complaint") },
                        navigationIcon = {
                            IconButton(onClick = {
                                navController.popBackStack()
                            }) {
                                Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Back")
                            }
                        },
                        backgroundColor = MaterialTheme.colors.surface,
                        elevation = elevationSize
                    )
                },
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(0.dp)
                ) {
                    if (loadingPositionState) {
                        CircularProgressIndicator(
                            modifier = Modifier.align(Alignment.Center),
                        )
                    }
                    viewLoadingState.apply {
                        if (isError) {
                            ErrorItem(
                                modifier = Modifier.align(Alignment.Center),
                                message = errorMessage
                            ) {
                                viewModel.getToPositionSend()
                            }
                        }
                        if (isSuccess) {
                            AddComplaintForm(
                                modifier = Modifier,
                                scrollState,
                                navController,
                                scope,
                                modalBottomSheetState,
                                viewModel
                            )
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class, ExperimentalAnimationApi::class)
@Composable
fun AddComplaintForm(
    modifier: Modifier,
    scrollState: LazyListState,
    navController: NavController,
    scope: CoroutineScope,
    modalBottomSheetState: ModalBottomSheetState,
    viewModel: AddComplaintViewModel
) {
    val complaintTitle by viewModel.title.observeAsState("")
    val complaintDescription by viewModel.description.observeAsState("")
    val isPrivate by viewModel.isPrivate.observeAsState(true)
    val isAnonymous by viewModel.isAnonymous.observeAsState(false)
    val imageUri by viewModel.imageUri.observeAsState(Uri.EMPTY)

    val listPosition by viewModel.listOfPosition
    val addComplaintState by viewModel.addComplaintState
    LaunchedEffect(key1 = true){
        if (addComplaintState.isLoading) {
            navController.navigate(Destination.AddComplaintConfirm.route)
        }
    }
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
                        onValueChange = { viewModel.setTitle(it) },
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
                        onValueChange = { viewModel.setDescription(it) },
                        singleLine = false,
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
                    DropDownMenuList(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp),
                        items = listPosition,
                        onItemSelected = { text, id ->
                            viewModel.setToPosition(Position(id, text))
                        },
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
                                        viewModel.setIsPrivate(!isPrivate)
                                        if (isAnonymous) {
                                            viewModel.setIsAnonymous(false)
                                        }
                                    }
                            )
                            Chip(
                                "Community", !isPrivate,
                                Modifier
                                    .padding(start = 0.dp, top = 8.dp, end = 8.dp, bottom = 8.dp)
                                    .clickable {
                                        viewModel.setIsPrivate(!isPrivate)
                                        if (isAnonymous) {
                                            viewModel.setIsAnonymous(false)
                                        }
                                    }
                            )
                        }
                    }
                }
            }
            val density = LocalDensity.current
            AnimatedVisibility(
                visible = !isPrivate,
                enter = slideInVertically(
                    // Start the slide from 40 (pixels) above where the content is supposed to go, to
                    // produce a parallax effect
                    initialOffsetY = { with(density) { -40.dp.roundToPx() } }
                ) + expandVertically(
                    expandFrom = Alignment.Top
                ) + scaleIn(
                    // Animate scale from 0f to 1f using the top center as the pivot point.
                    transformOrigin = TransformOrigin(0.5f, 0f)
                ) + fadeIn(initialAlpha = 0.3f),
                exit = slideOutVertically() + shrinkVertically() + fadeOut() + scaleOut(targetScale = 1.2f)
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
                                        viewModel.setIsAnonymous(!isAnonymous)
                                    }
                            )
                            Chip(
                                "Show", !isAnonymous,
                                Modifier
                                    .padding(start = 0.dp, top = 8.dp, end = 8.dp, bottom = 8.dp)
                                    .clickable {
                                        viewModel.setIsAnonymous(!isAnonymous)
                                    }
                            )
                        }
                    }
                }
            }
        }
        item{
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
                        text = "Image Preview",
                        color = MaterialTheme.colors.primary,
                        style = MaterialTheme.typography.h6
                    )
                    Text(
                        text = "Add a photo to your report",
                        color = Color.LightGray,
                        style = MaterialTheme.typography.subtitle1
                    )
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                    ) {
                        if (imageUri != Uri.EMPTY){
                            Box(
                                modifier = Modifier
                                    .padding(16.dp)
                                    .height(250.dp),
                            ){
                                Image(
                                    painter = rememberAsyncImagePainter(
                                        imageUri
                                    ),
                                    contentDescription = complaintTitle,
                                    contentScale = ContentScale.FillHeight,
                                    modifier = Modifier
                                        .fillParentMaxHeight()
                                        .clip(RoundedCornerShape(8.dp)),
                                    alignment = Alignment.Center
                                )
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_baseline_clear_24),
                                    contentDescription = "Clear Image",
                                    tint = Color.White,
                                    modifier = Modifier
                                        .padding(8.dp)
                                        .align(Alignment.TopEnd)
                                        .clip(CircleShape)
                                        .background(color = Color.DarkGray.copy(alpha = 0.8F))
                                        .padding(1.dp)
                                        .clickable {
                                            viewModel.setImageUri(Uri.EMPTY)
                                        }
                                    ,
                                )
                            }
                        }
                        else {
                            Box(
                                modifier = Modifier
                                    .height(100.dp)
                                    .clickable {
                                        scope.launch {
                                            modalBottomSheetState.show()
                                        }
                                    },
                            ){
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .fillParentMaxHeight()
                                        .border(
                                            color = Color.LightGray,
                                            width = 1.dp,
                                            shape = RoundedCornerShape(5.dp)
                                        ),
                                ){
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic__image_add_24_filled),
                                        contentDescription = "Add Image",
                                        tint = Color.LightGray,
                                        modifier = Modifier
                                            .align(alignment = Alignment.Center)
                                        ,
                                    )
                                }

                            }
                        }
                    }
                }
            }
        }
        item {
            Spacer(modifier = Modifier
                .height(1.dp)
                .background(color = Color.LightGray))
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
                    horizontalArrangement = Arrangement.End
                ) {
                    Button(
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = MaterialTheme.colors.surface,
                            contentColor = MaterialTheme.colors.primary
                        ),
                        border = BorderStroke(
                            width = 1.dp,
                            color = MaterialTheme.colors.primary
                        ),
                        onClick = {
                            viewModel.clearForm()
                        }
                    ) {
                        Text(text = "Clear")
                    }
                    Spacer(modifier = Modifier
                        .width(8.dp))
                    Button(
                        onClick = {
                            viewModel.submitForm()
                        }
                    ) {
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
        OutlinedTextField(
            value = "Lorem",
            onValueChange = {  },
            singleLine = true,
            modifier = Modifier
                .padding(top = 8.dp)
        )

    }
}
