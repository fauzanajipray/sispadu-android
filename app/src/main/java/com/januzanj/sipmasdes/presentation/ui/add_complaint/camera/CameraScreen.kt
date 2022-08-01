package com.januzanj.sipmasdes.presentation.ui.add_complaint.camera

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.FabPosition
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavHostController
import com.januzanj.sipmasdes.presentation.navigation.Destination
import kotlin.io.path.absolutePathString

@Composable
fun CameraScreen(
    navController: NavHostController,
    cameraX: CameraX,
    cameraViewModel: CameraViewModel
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                backgroundColor = Color.White,
                onClick = {
                    cameraX.takePhoto(){
                        cameraViewModel.setImageUri(it)
                        navController.popBackStack()
                    }
                }
            ) {}
        },
        floatingActionButtonPosition = FabPosition.Center
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            AndroidView(
                modifier = Modifier.fillMaxSize(),
                factory = { cameraX.startCameraPreviewView() }
            )
        }
    }
}
