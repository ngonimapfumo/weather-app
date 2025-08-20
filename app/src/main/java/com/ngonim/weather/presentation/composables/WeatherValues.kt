package com.ngonim.weather.presentation.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ngonim.weather.data.model.GetCurrentWeatherResponse
import com.ngonim.weather.data.model.GetCurrentWeatherResponse.Location

@Composable
fun WeatherValues(key:String, value: String) {
    Column(modifier = Modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally){
        Text(text = value, fontSize = 24.sp,
            fontWeight = FontWeight.Light)
        Text(key, fontWeight = FontWeight.SemiBold,
            color = Color.Gray,
            fontSize = 12.sp)
    }
}
@Preview
@Composable
fun WeatherValuesPreview() {
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