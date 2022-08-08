package com.januzanj.sipmasdes.presentation.ui.add_complaint

import android.content.SharedPreferences
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.januzanj.sipmasdes.common.Constant
import com.januzanj.sipmasdes.common.StatusMessage
import com.januzanj.sipmasdes.data.source.remote.response.ComplaintRequest
import com.januzanj.sipmasdes.domain.model.Position
import com.januzanj.sipmasdes.domain.use_case.user.GetPositionsUseCase
import com.januzanj.sipmasdes.domain.use_case.user.PostComplaintUseCase
import com.januzanj.sipmasdes.domain.use_case.user.PostNewComplaintUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject

@HiltViewModel
class AddComplaintViewModel @Inject constructor(
    private val getPositionsUseCase: GetPositionsUseCase,
    private val postComplaintUseCase: PostComplaintUseCase,
    private val postNewComplaintUseCase: PostNewComplaintUseCase,
    private val pref: SharedPreferences
) : ViewModel() {

    companion object {
        const val TAG = "AddComplaintViewModel"
    }

    private var token = ""
    private var userId = 0
    init {
        getToPositionSend()
        token = pref.getString(Constant.PREF_KEY_USER_TOKEN, "") ?: ""
        userId = pref.getString(Constant.PREF_KEY_USER_ID, "0")?.toInt() ?: 0
    }

    private val _listOfPosition = mutableStateOf(listOf<Position>())
    val listOfPosition: State<List<Position>> = _listOfPosition


    private val _getPositionLoadingState = mutableStateOf(false)
    val getPositionLoadingState: State<Boolean> = _getPositionLoadingState
    private val _getPositionState : MutableState<GetPositionState> = mutableStateOf(GetPositionState())
    val getPositionState : State<GetPositionState>
        get() = _getPositionState

    private val _addComplaintState = mutableStateOf(AddComplaintState())
    val addComplaintState: State<AddComplaintState> = _addComplaintState

    private val _title = MutableLiveData("")
    val title : LiveData<String>
        get() = _title

    private val _description = MutableLiveData("")
    val description : LiveData<String>
        get() = _description

    private val _toPosition = MutableLiveData<Position>(Position())
    val toPosition : LiveData<Position>
        get() = _toPosition

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

    fun setToPosition(position: Position) {
        _toPosition.postValue(position)
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


    fun clearForm() {
        _title.value = ""
        _description.value = ""
        _imageUri.value = Uri.EMPTY
        _isPrivate.value = true
        _isAnonymous.value = false
    }

    fun getToPositionSend(){
        getPositionsUseCase().onEach {
            when (it.status){
                StatusMessage.LOADING -> {
                    Log.d(TAG, "getToPositionSend: loading")
//                    _getPositionLoadingState.value = true
                }
                StatusMessage.SUCCESS -> {
                    _listOfPosition.value = it.data?.data!!
                    _getPositionLoadingState.value = false
                    _getPositionState.value = GetPositionState(isSuccess = true, isError = false)
                }
                StatusMessage.ERROR -> {
                    val error = it.message ?: "Something went wrong"
                    _getPositionLoadingState.value = false
                    _getPositionState.value = GetPositionState(isError = true, errorMessage = error, isSuccess = false)
                }
                else -> {
                    _getPositionLoadingState.value = false
                    _getPositionState.value = GetPositionState(isError = true, isSuccess = false, errorMessage = "Something went wrong")
                }
            }
        }.launchIn(viewModelScope)
    }

    fun submitForm() {
        Log.d(TAG, "submitForm: ")
        val file = File(_imageUri.value?.path ?: "")
        if (file.path.toString().isNotEmpty()) {
            Log.d(TAG, "submitForm: file exists")
            val requestBody = ComplaintRequest(
                user_id = userId,
                title = _title.value ?: "",
                description = _description.value ?: "",
                is_private = if (_isPrivate.value == true) 1 else 0,
                is_anonymous = if (_isAnonymous.value == true) 1 else 0,
                position_id = toPosition.value?.id,
                image = file
            )
            Log.d(TAG, "submitForm: $requestBody")
            postNewComplaintUseCase(token, requestBody).onEach {
                when (it.status){
                    StatusMessage.LOADING -> {
                        _addComplaintState.value = AddComplaintState(isLoading = true)
                    }
                    StatusMessage.SUCCESS -> {
                        _addComplaintState.value = AddComplaintState(isSuccess = true, isLoading = false, isError = false)
                    }
                    StatusMessage.ERROR -> {
                        val error = it.message ?: "Something went wrong"
                        _addComplaintState.value = AddComplaintState(isError = true, isLoading = false, errorMessage = error, isSuccess = false)
                    }
                    else -> {
                        _addComplaintState.value = AddComplaintState(isError = true, isLoading = false, isSuccess = false, errorMessage = "Something went wrong")
                    }
                }
            }.launchIn(viewModelScope)
        }
    }
}
