package com.januzanj.sipmasdes.presentation.ui.home

sealed class ComplaintListEvent {

    object NewComplaintEvent : ComplaintListEvent()

    object RefreshComplaintListEvent : ComplaintListEvent()

    object NextPage : ComplaintListEvent()

}