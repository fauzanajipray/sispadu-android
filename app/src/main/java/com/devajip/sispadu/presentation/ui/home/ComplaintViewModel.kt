package com.devajip.sispadu.presentation.ui.home

import android.content.SharedPreferences
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.devajip.sispadu.common.Constant
import com.devajip.sispadu.common.StatusMessage
import com.devajip.sispadu.data.source.remote.response.ComplaintsRequestParams
import com.devajip.sispadu.domain.use_case.user.GetComplaintDetailUseCase
import com.devajip.sispadu.domain.use_case.user.GetComplaintPagingUseCase
import com.devajip.sispadu.presentation.ui.home.detail.ComplaintDetailState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class ComplaintViewModel @Inject constructor(
    private val getComplaintPagingUseCase: GetComplaintPagingUseCase,
    private val getComplaintDetailUseCase: GetComplaintDetailUseCase,
    private val pref: SharedPreferences,
) : ViewModel() {

    var token: String = ""

    private val _complaintsRequestParams = mutableStateOf(ComplaintsRequestParams())
    val complaintsRequestParams: State<ComplaintsRequestParams> = _complaintsRequestParams

    private val _stateDetail = mutableStateOf(ComplaintDetailState())
    val stateDetail: State<ComplaintDetailState> = _stateDetail

    init {
        token = pref.getString(Constant.PREF_KEY_USER_TOKEN, "") ?: ""

    }

    val complaintList = getComplaintPagingUseCase(token, _complaintsRequestParams.value).cachedIn(viewModelScope)

    fun getComplaintDetail(complaintId: Int) {
        getComplaintDetailUseCase(token, complaintId).onEach {
            when (it.status) {
                StatusMessage.LOADING -> {
                    _stateDetail.value = ComplaintDetailState(
                        isLoading = true,
                        isSuccess = false,
                        complaint = null,
                    )
                }
                StatusMessage.SUCCESS -> {
                    _stateDetail.value = ComplaintDetailState(
                        isLoading = false,
                        isSuccess = true,
                        complaint = it.data?.data
                    )
                }
                StatusMessage.ERROR -> {
                    _stateDetail.value = ComplaintDetailState(
                        isLoading = false,
                        isSuccess = false,
                        error = it.message ?: "Error Occurred"
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

//    fun getComplaintDetail(complaintId: Int): Flow<Resource<ComplaintDetailResponse>> {
//        return getComplaintDetailUseCase(token, complaintId)
//    }

}