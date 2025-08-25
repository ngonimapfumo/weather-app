package com.ngonim.weather.presentation.weather

import SettingsPage
import android.app.UiModeManager.MODE_NIGHT_YES
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.NotificationsActive
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import com.ngonim.weather.presentation.pages.alerts.WeatherAlertsPage
import com.ngonim.weather.presentation.pages.current.WeatherPage
import com.ngonim.weather.presentation.model.TabItem
import com.ngonim.weather.presentation.theme.WeatherTheme
import com.ngonim.weather.util.ConfigStore

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel = ViewModelProvider(this)[WeatherViewModel::class.java]
        enableEdgeToEdge()
        val tabItems = listOf(
            TabItem(
                title = "Current",
                selectedIcon = Icons.Filled.LocationOn,
                unSelectedIcon = Icons.Outlined.LocationOn
            ),
            TabItem(
                title = "Alerts",
                selectedIcon = Icons.Filled.NotificationsActive,
                unSelectedIcon = Icons.Outlined.NotificationsActive
            ))
            /*TabItem(
                title = "Settings",
                selectedIcon = Icons.Filled.Settings,
                unSelectedIcon = Icons.Outlined.Settings
            ))*/
        setContent {
            WeatherTheme(dynamicColor = true) {
                Scaffold(
                    modifier = Modifier.Companion.fillMaxSize()
                ) { innerPadding ->

                    Surface(
                        modifier = Modifier.Companion.padding(innerPadding),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        var selectedTabIndex by remember {
                            mutableIntStateOf(0)
                        }
                        val pagerState = rememberPagerState {
                            tabItems.size
                        }
                        LaunchedEffect(selectedTabIndex) {
                            pagerState.animateScrollToPage(selectedTabIndex)
                        }
                        LaunchedEffect(pagerState.currentPage, pagerState.isScrollInProgress) {
                            if (!pagerState.isScrollInProgress) {
                                selectedTabIndex = pagerState.currentPage
                            }
                        }
                        Column(modifier = Modifier.fillMaxSize()){
                            TabRow(selectedTabIndex = selectedTabIndex) {
                                tabItems.forEachIndexed { index, tabItem ->
                                    Tab(
                                        index == selectedTabIndex,
                                        onClick = {
                                            selectedTabIndex = index
                                        },
                                        text = {
                                            Text(tabItem.title)
                                        }, icon = {
                                            Icon(
                                                imageVector = if (index == selectedTabIndex) {
                                                    tabItem.selectedIcon
                                                } else {
                                                    tabItem.unSelectedIcon
                                                },
                                                contentDescription = null
                                            )
                                        }
                                    )
                                }
                            }
                            HorizontalPager(pagerState,
                                modifier = Modifier.fillMaxWidth()
                                    .weight(1f)) {
                                val tabItemTitle = tabItems[it].title
                                when (tabItemTitle) {
                                    "Current" -> {
                                        Column(modifier = Modifier.fillMaxSize()) {WeatherPage(viewModel)}

                                    }
                                    "Alerts" -> {
                                        Column(modifier = Modifier.fillMaxSize()) {WeatherAlertsPage(viewModel) }
                                    }

                                    /*"Settings" -> {
                                        Column(modifier = Modifier.fillMaxSize()) {
                                            SettingsPage()
                                        }
                                    }*/

                                }
                            }
                        }
                    }
                }
            }
        }
    }
@Preview
@Composable
fun Preview(){
    WeatherPage(null)
}
}