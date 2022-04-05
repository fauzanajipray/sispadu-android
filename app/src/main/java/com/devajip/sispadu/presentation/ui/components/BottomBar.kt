package com.devajip.sispadu.presentation.ui.components

import android.content.res.Configuration
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

import com.devajip.sispadu.presentation.ui.main.MainScreenTab
import com.devajip.sispadu.presentation.ui.main.MainViewModel
import com.devajip.sispadu.presentation.theme.SispaduTheme
import com.google.accompanist.insets.navigationBarsHeight
import com.google.accompanist.insets.navigationBarsPadding

@Composable
fun BottomBar(
    viewModel: MainViewModel,
    tabs: Array<MainScreenTab>,
    selectedTab: MainScreenTab,
) {
    BottomNavigation(
        backgroundColor = MaterialTheme.colors.surface,
        modifier = Modifier
            .navigationBarsHeight(56.dp)
    ) {
        tabs.forEach { tab ->
            val icon = if (tab == selectedTab) tab.selectedIcon else tab.icon
            BottomNavigationItem(
                icon = { Icon(painter = painterResource(id = icon), contentDescription = stringResource(id = tab.title) ) },
                selected = tab == selectedTab,
                onClick = { viewModel.selectTab(tab) },
                selectedContentColor = MaterialTheme.colors.primary,
                unselectedContentColor = MaterialTheme.colors.onSurface,
                modifier = Modifier.navigationBarsPadding()
            )
        }
    }
}

@Preview("BottomBar ")
@Preview("BottomBar (dark theme)", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun DefaultPreview(){
    SispaduTheme() {
        BottomBar(
            viewModel = MainViewModel(),
            tabs = arrayOf(MainScreenTab.HOME, MainScreenTab.HISTORY, MainScreenTab.PROFILE),
            selectedTab = MainScreenTab.HOME
        )
    }
}
