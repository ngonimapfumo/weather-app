package com.ngonim.weather.presentation.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.ngonim.weather.data.model.GetCurrentWeatherResponse
import com.ngonim.weather.data.model.GetCurrentWeatherResponse.Location


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
                modifier = Modifier
                    .size(40.dp)
                    .padding(start = 8.dp)
                    .padding(top = 12.dp)
            )
            Text(
                modifier = Modifier.padding(start = 8.dp),
                text = data.location?.name.toString(),
                fontSize = 28.sp,
                fontWeight = FontWeight.Light
            )
            Spacer(modifier = Modifier.width(8.dp))
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 50.dp)
        ) {
            Text(
                text = data.location?.country.toString(),
                fontWeight = FontWeight.Light

            )
            Text(
                text = data.location?.region.toString(),
                color = Color.Gray,
                fontWeight = FontWeight.Light
            )
        }
        //  Spacer(modifier = Modifier.height(16.dp))

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
                .replace("64x64", "128x128"),
            contentDescription = "Weather Icon"
        )
        Text(
            text = data.current?.condition?.text.toString(),
            fontSize = 20.sp,
            fontWeight = FontWeight.Light,
            fontStyle = FontStyle.Normal,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.padding(8.dp))
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            elevation = CardDefaults.cardElevation(
                pressedElevation = 2.dp,
                defaultElevation = 2.dp,
                focusedElevation = 2.dp,
                hoveredElevation = 2.dp
            )
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    WeatherValues("Wind speed", data.current?.windKph.toString())
                    WeatherValues("Humidity", data.current?.humidity.toString())

                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    WeatherValues("Precipitation", data.current?.precipIn.toString())
                    WeatherValues("UV Index", data.current?.uv.toString())

                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    WeatherValues("Heat index", data.current?.heatindexC.toString())
                    WeatherValues(" Cloud cover", data.current?.cloud.toString())

                }
            }
        }


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