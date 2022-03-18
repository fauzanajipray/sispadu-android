package com.devajip.sispadu.presentation.navigation

sealed class Destination(val route: String) {
    object Login : Destination(route = "login")
    object Home : Destination(route = "home")
    object ComplaintDetail : Destination(route = "complaint_detail")
    object History : Destination(route = "history")
    object News : Destination(route = "news")
    object Profile : Destination(route = "profile")
    object AddComplaint : Destination(route = "add_complaint")

    companion object {
        fun getStartDestination() = Login.route
    }
}