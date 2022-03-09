package com.devajip.sispadu.presentation.navigation

sealed class Destination(val route: String) {
    object Login : Destination(route = "login")
    object Home : Destination(route = "home")
    object Community : Destination(route = "community")
    object News : Destination(route = "news")
    object Profile : Destination(route = "profile")

    companion object {
        fun getStartDestination() = Login.route
    }
}