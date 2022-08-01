package com.januzanj.sipmasdes.presentation.ui.add_complaint

import android.net.Uri
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddComplaintViewModel @Inject constructor() : ViewModel() {

    private val _savedUriState = mutableStateOf(Uri.EMPTY)
    var savedUri: State<Uri?> = _savedUriState

    private val _title = MutableLiveData("")
    val title : LiveData<String>
        get() = _title

    private val _description = MutableLiveData("")
    val description : LiveData<String>
        get() = _description

    private val _imageUri = MutableLiveData(Uri.EMPTY)
    val imageUri : LiveData<Uri?>
        get() = _imageUri

    private val _isPrivate = MutableLiveData(true)
    val isPrivate : LiveData<Boolean>
        get() = _isPrivate

    private val _isAnonymous = MutableLiveData(false)
    val isAnonymous : LiveData<Boolean>
        get() = _isAnonymous


    fun setTitle(title: String) {
        _title.postValue(title)
    }

    fun setDescription(description: String) {
        _description.postValue(description)
    }

    fun setImageUri(uri: Uri?) {
        _imageUri.postValue(uri)
    }

    fun setIsPrivate(isPrivate: Boolean) {
        _isPrivate.postValue(isPrivate)
    }

    fun setIsAnonymous(isAnonymous: Boolean) {
        _isAnonymous.postValue(isAnonymous)
    }

}
