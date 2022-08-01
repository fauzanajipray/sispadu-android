package com.januzanj.sipmasdes.presentation.ui.add_complaint.camera

import android.net.Uri
import androidx.compose.runtime.MutableState
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CameraViewModel @Inject constructor(): ViewModel() {

    private val _imageUri = MutableLiveData(Uri.EMPTY)
    val imageUri: LiveData<Uri> = _imageUri

    fun setImageUri(uri: Uri) {
        _imageUri.value = uri
    }
}