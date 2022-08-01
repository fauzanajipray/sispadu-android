package com.januzanj.sipmasdes.presentation.ui.add_complaint

import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.navigation.NavController
import com.januzanj.sipmasdes.presentation.navigation.Destination
import com.januzanj.sipmasdes.R
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.MultiplePermissionsState

@Composable
fun BottomSheetItem(icon: Int, title: String, onItemClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = { onItemClick() })
            .height(55.dp)
            .background(color = MaterialTheme.colors.surface)
            .padding(start = 15.dp), verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(painter = painterResource(id = icon), contentDescription = "Share", tint = MaterialTheme.colors.onSurface)
        Spacer(modifier = Modifier.width(20.dp))
        Text(text = title, color = MaterialTheme.colors.onSurface)
    }
}

@Preview(showBackground = true)
@Composable
fun BottomSheetListItemPreview() {
    BottomSheetItem(icon = R.drawable.ic_baseline_camera_alt_24, title = "Take photo", onItemClick = {

    })
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun BottomSheetContent(
    navController: NavController,
    permissionsState: MultiplePermissionsState,
    launcherGallery: ManagedActivityResultLauncher<String, Uri?>
){
    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(
        key1 = lifecycleOwner,
        effect = {
            val observer = LifecycleEventObserver{ _, event ->
                if (event == Lifecycle.Event.ON_START) {
                    permissionsState.launchMultiplePermissionRequest()
                }
            }
            lifecycleOwner.lifecycle.addObserver(observer)
            onDispose {
                lifecycleOwner.lifecycle.removeObserver(observer)
            }
        }
    )

    Column {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(35.dp)
                .background(color = MaterialTheme.colors.surface)
                .padding(start = 15.dp)
        ) {
            Column(modifier = Modifier
                .width(35.dp)
                .background(color = Color.LightGray)
                .height(5.dp)
                .align(Alignment.Center)
                .clip(shape = RoundedCornerShape(5.dp))
            ){}
        }
        BottomSheetItem(
            icon = R.drawable.ic_baseline_camera_alt_24,
            title = "Take photo",
            onItemClick = {
                if (permissionsState.allPermissionsGranted) {
                    navController.navigate(Destination.Camera.route)
                } else {
                    permissionsState.launchMultiplePermissionRequest()
                }
            })
        BottomSheetItem(
            icon = R.drawable.ic_baseline_folder_open_24,
            title = "Insert from gallery",
            onItemClick = {
                if (permissionsState.allPermissionsGranted) {
                    launcherGallery.launch("image/*")
                } else {
                    permissionsState.launchMultiplePermissionRequest()
                }
            })
    }
}

@Preview(showBackground = true)
@Composable
fun BottomSheetContentPreview() {
//    BottomSheetContent()
}