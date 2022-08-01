package com.januzanj.sipmasdes.presentation.ui.home

import android.content.SharedPreferences
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.januzanj.sipmasdes.common.Constant
import com.januzanj.sipmasdes.common.StatusMessage
import com.januzanj.sipmasdes.data.source.remote.response.ComplaintsRequestParams
import com.januzanj.sipmasdes.domain.model.Complaint
import com.januzanj.sipmasdes.domain.use_case.user.GetComplaintsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getComplaintsUseCase: GetComplaintsUseCase,
    private val savedStateHandle: SavedStateHandle,
    private val pref: SharedPreferences,
) : ViewModel() {

    companion object {
        const val PAGE_SIZE = 20
        const val TAG = "HomeViewModel"
        const val STATE_KEY_PAGE =  "complaints.state.key.page"
    }

    private val _state = mutableStateOf(HomeViewState())
    val state: State<HomeViewState> = _state

    private val _complaintsRequestParams = mutableStateOf(ComplaintsRequestParams())
    val complaintsRequestParams: State<ComplaintsRequestParams> = _complaintsRequestParams

    private var complaintListScrollPosition = 0
    var token: String = ""

    init {
        token = pref.getString(Constant.PREF_KEY_USER_TOKEN, "") ?: ""

        savedStateHandle.get<Int>(STATE_KEY_PAGE)?.let { p ->
            setPage(p)
        }

        newComplaintEvent()
    }

    private fun incrementPage() {
        setPage(state.value.page + 1)
    }

    private fun setPage(page: Int) {
        _state.value.page = page
        savedStateHandle.set(STATE_KEY_PAGE, page)
    }

    private fun appendComplaints(complaints: List<Complaint>) {
        val current = ArrayList(_state.value.complaints)
        Log.d(TAG, "appendComplaints: current.size = ${current.size}")
        current.addAll(complaints)
        Log.d(TAG, "appendComplaints after: current.size = ${current.size}")
        _state.value.complaints = current
    }

    fun onChangeScrollPosition(position: Int) {
        complaintListScrollPosition = position
    }

    fun onTriggerEvent(event: ComplaintListEvent){
        try {
            when (event) {
                is ComplaintListEvent.NewComplaintEvent -> {
                    newComplaintEvent()
                }
                is ComplaintListEvent.NextPage -> {
                    nextPageEvent()
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "onTriggerEvent: ", e)
            e.printStackTrace()
        }
    }

    private fun nextPageEvent() {
        if ((complaintListScrollPosition + 1) >= (_state.value.page * _state.value.perPage)) {
            incrementPage()
            Log.i(TAG, "nextPage: triggered: ${_state.value.page} ${_state.value.perPage}")
            Log.d(TAG, "nextPage: Total Page: ${_state.value.totalPages}")
            Log.i(TAG, "nextPage: Complaints size: ${_state.value.complaints.size}")
            if (_state.value.page <= _state.value.totalPages) {
                Log.d(TAG, "nextPage: fetching page: ${_state.value.page}")
                val requestParams = _complaintsRequestParams.value.copy(
//                    page = _state.value.page
                )
                getComplaintsUseCase(token, requestParams).onEach {
                    delay(1000)
                    when (it.status) {
                        StatusMessage.LOADING -> {
                            _state.value.isLoading = true
                        }
                        StatusMessage.SUCCESS -> {
                            Log.d(TAG, "nextPage: current complaint appending")
                            it.data?.data?.let { complaints -> appendComplaints(complaints.data) }
                            _state.value.apply {
                                isLoading = false
                                isSuccess = true
                            }
                        }
                        StatusMessage.ERROR -> {
                            _state.value.error = it.message ?: "Error Occurred"
                            _state.value.isLoading = false
                        }
                        else -> {
                            _state.value.error = "Error Occurred"
                            _state.value.isLoading = false
                        }
                    }
                }.launchIn(viewModelScope)
            }
        }
    }

    private fun newComplaintEvent() {
        val requestParams = _complaintsRequestParams.value.copy(
//            page = _state.value.page
        )
        getComplaintsUseCase(token, requestParams).onEach {
            when (it.status) {
                StatusMessage.LOADING -> {
                    _state.value = HomeViewState(isLoading = true)
                }
                StatusMessage.SUCCESS -> {
                    val perPage = it.data?.data?.let { it1 -> it1.perPage } ?: 0
                    val totalPage = it.data?.data?.let { it1 -> it1.lastPage } ?: 0
                    Log.d(TAG, "complaint : appending")
                    _state.value = HomeViewState(
                        isLoading = false,
                        isSuccess = true,
                        perPage = perPage,
                        totalPages = totalPage,
                        complaints = it.data?.data?.let { it1 -> it1.data } ?: emptyList()
                    )
                }
                StatusMessage.ERROR -> {
                    _state.value = HomeViewState(error = it.message ?: "Error Occurred")
                    _state.value = HomeViewState(isLoading = false)
                }
                else -> {
                    _state.value = HomeViewState(error = "Error Occurred")
                    _state.value = HomeViewState(isLoading = false)
                }
            }
        }.launchIn(viewModelScope)
    }
}