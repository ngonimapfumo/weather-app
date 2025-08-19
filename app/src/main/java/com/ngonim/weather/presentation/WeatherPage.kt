package com.ngonim.weather.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
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
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.ngonim.weather.data.model.GetCurrentWeatherResponse
import com.ngonim.weather.data.model.GetCurrentWeatherResponse.Location
import com.ngonim.weather.data.util.NetworkResponse


@Composable
fun WeatherPage(viewModel: WeatherViewModel?) {
    var city by remember {
        mutableStateOf("")
    }
    val weatherResult = viewModel?.weatherResult?.observeAsState()

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
                value = city,
                onValueChange = {
                    city = it
                }, label = {
                    Text("Search for any location")
                }
            )
            IconButton(onClick = {
                viewModel?.fetchWeather(city)
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
                ){Text(text = result.message)}

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

@Composable
fun WeatherDetails(data: GetCurrentWeatherResponse) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.Start

        ) {
            Icon(
                imageVector = Icons.Default.LocationOn,
                contentDescription = "Location Icon",
                modifier = Modifier.size(40.dp).padding(start = 8.dp),
                tint = Color.Red
            )
            Text(
                modifier = Modifier.padding(start = 8.dp),
                text = data.location?.name.toString(),
                fontSize = 28.sp,
                fontWeight = FontWeight.Light
            )
            Spacer(modifier = Modifier.width(8.dp))

            Column {
                Text(
                    text = data.location?.country.toString(),
                    color = Color.Gray
                )
                Text(text = data.location?.region.toString(),
                    fontWeight = FontWeight.Light)
            }



        }
        Spacer(modifier = Modifier.height(16.dp))

        Row {
            Text(
                text = data.current?.tempC.toString(),
                fontSize = 56.sp,
                fontWeight = FontWeight.ExtraLight,
                fontStyle = FontStyle.Normal,
                textAlign = TextAlign.Center
            )
            Text(text = "°C")


        }
        Spacer(modifier = Modifier.height(16.dp))

        AsyncImage(
            modifier = Modifier.size(160.dp),
            model = "https:${data.current?.condition?.icon}"
                .replace("64x64","128x128"),
            contentDescription = "Weather Icon"
        )
        Text(
            text = data.current?.condition?.text.toString(),
            fontSize = 20.sp,
            fontWeight = FontWeight.Light,
            fontStyle = FontStyle.Normal,
            textAlign = TextAlign.Center
        )

    }
}

@Preview
@Composable
fun WeatherDetailsPreview() {
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

