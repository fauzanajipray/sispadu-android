package com.januzanj.sipmasdes.presentation.navigation

sealed class Destination(val route: String) {
    object Splash : Destination("splash")
    object Login : Destination(route = "login")
    object Home : Destination(route = "home")
    object ComplaintDetail : Destination(route = "complaint_detail")
    object History : Destination(route = "history")
    object News : Destination(route = "news")
    object Profile : Destination(route = "profile")
    object Camera : Destination(route = "camera")
    object AddComplaint : Destination(route = "add_complaint")
    object AddComplaintConfirm : Destination(route = "add_complaint_confirm")

    companion object {
        fun getStartDestination() = Splash.route
    }
}