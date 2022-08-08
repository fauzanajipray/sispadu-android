package com.januzanj.sipmasdes.presentation.ui.add_complaint.camera

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.FabPosition
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavHostController
import com.januzanj.sipmasdes.presentation.ui.add_complaint.AddComplaintViewModel

@Composable
fun CameraScreen(
    navController: NavHostController,
    cameraX: CameraX,
    viewModel: AddComplaintViewModel
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier.padding(bottom = 25.dp),
                backgroundColor = Color.White,
                onClick = {
                    cameraX.takePhoto(){
                        viewModel.setImageUri(it)
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
