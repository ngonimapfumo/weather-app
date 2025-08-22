package com.ngonim.weather.presentation.weather

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddAlert
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.LocationSearching
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.outlined.AddAlert
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.LocationSearching
import androidx.compose.material.icons.outlined.NotificationsActive
import androidx.compose.material.icons.outlined.PeopleOutline
import androidx.compose.material.icons.outlined.Phone
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import com.ngonim.weather.presentation.composables.WeatherPage
import com.ngonim.weather.presentation.model.TabItem
import com.ngonim.weather.presentation.theme.WeatherTheme

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
        setContent {
            WeatherTheme {
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
                                        Column(modifier = Modifier.fillMaxSize()) {WeatherPage(viewModel)}
                                    }
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