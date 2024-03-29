package com.januzanj.sipmasdes.presentation.ui.main

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.januzanj.sipmasdes.R
import com.januzanj.sipmasdes.presentation.navigation.Destination

enum class MainScreenTab(
    @DrawableRes val icon: Int,
    @DrawableRes val selectedIcon: Int,
    @StringRes val title: Int,
    val route: Destination
) {
    HOME(R.drawable.ic_home_regular, R.drawable.ic_home_filled, R.string.home, Destination.Home),
    HISTORY(R.drawable.ic_history, R.drawable.ic_history, R.string.history, Destination.History),
//    NEWS(R.drawable.ic_news_regular, R.drawable.ic_news_filled, R.string.news, Destination.News),
    PROFILE(R.drawable.ic_person_regular, R.drawable.ic_person_filled, R.string.profile, Destination.Profile);
}
