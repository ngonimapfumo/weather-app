package com.ngonim.weather.presentation.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ngonim.weather.data.model.GetCurrentWeatherResponse
import com.ngonim.weather.data.model.GetCurrentWeatherResponse.Location
import com.ngonim.weather.data.util.NetworkResponse
import com.ngonim.weather.presentation.weather.WeatherViewModel


@Composable
fun WeatherPage(viewModel: WeatherViewModel?) {
    var city by remember {
        mutableStateOf("")
    }
    val weatherResult = viewModel?.weatherResult?.observeAsState()
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            OutlinedTextField(
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(25.dp),
                maxLines = 1,
                singleLine = true,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(onSearch = {
                    viewModel?.fetchWeather(city)
                    keyboardController?.hide()
                }),
                value = city,
                onValueChange = {
                    city = it
                }, label = {
                    Text("Search for any location")
                }
            )
            IconButton(onClick = {
                viewModel?.fetchWeather(city)
                keyboardController?.hide()
            }) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search Icon"
                )
            }

        }
        when (val result = weatherResult?.value) {
            is NetworkResponse.Error -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) { Text(text = result.message) }

            }

            NetworkResponse.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }

            }

            is NetworkResponse.Success -> {
                WeatherDetails(data = result.data)
            }

            null -> {}
        }
    }
}





@Preview
@Composable
fun WeatherPagePreview() {
    val data = GetCurrentWeatherResponse(
        current = null,
        location = Location(
            name = "London",
            country = "United Kingdom",
            lat = 51.52,
            lon = -0.11, localtime = "2023-11-22 10:30",
            localtimeEpoch = 1699981800,
            region = "City of London, Greater London",
            tzId = "Europe/London"
        )
    )
    WeatherDetails(data = data)
}

