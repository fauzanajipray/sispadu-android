package com.devajip.sispadu.presentation.ui.home

sealed class ComplaintListEvent {

    object NewComplaintEvent : ComplaintListEvent()

    object RefreshComplaintListEvent : ComplaintListEvent()

    object NextPage : ComplaintListEvent()

}