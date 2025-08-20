package com.ngonim.weather.presentation.weather

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import com.ngonim.weather.presentation.composables.WeatherPage
import com.ngonim.weather.presentation.theme.WeatherTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel = ViewModelProvider(this)[WeatherViewModel::class.java]
        enableEdgeToEdge()
        setContent {
            WeatherTheme {
                Scaffold(
                    modifier = Modifier.Companion.fillMaxSize()
                ) { innerPadding ->

                    Surface(
                        modifier = Modifier.Companion.padding(innerPadding),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        WeatherPage(viewModel)
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