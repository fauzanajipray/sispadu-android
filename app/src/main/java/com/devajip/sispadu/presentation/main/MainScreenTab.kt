package com.devajip.sispadu.presentation.main

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.devajip.sispadu.R
import com.devajip.sispadu.presentation.navigation.Destination

enum class MainScreenTab(
    @DrawableRes val icon: Int,
    @DrawableRes val selectedIcon: Int,
    @StringRes val title: Int,
    val route: Destination
) {
    HOME(R.drawable.ic_home_regular, R.drawable.ic_home_filled, R.string.home, Destination.Home),
    COMMUNITY(R.drawable.ic_community_reguler, R.drawable.ic_community_filled, R.string.community, Destination.Community),
    NEWS(R.drawable.ic_news_regular, R.drawable.ic_news_filled, R.string.news, Destination.News),
    PROFILE(R.drawable.ic_person_regular, R.drawable.ic_person_filled, R.string.profile, Destination.Profile);
}
