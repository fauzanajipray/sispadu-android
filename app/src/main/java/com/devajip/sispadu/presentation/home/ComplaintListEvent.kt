package com.devajip.sispadu.presentation.home

sealed class ComplaintListEvent {

    object NewComplaintEvent : ComplaintListEvent()

    object RefreshComplaintListEvent : ComplaintListEvent()

    object NextPage : ComplaintListEvent()

}