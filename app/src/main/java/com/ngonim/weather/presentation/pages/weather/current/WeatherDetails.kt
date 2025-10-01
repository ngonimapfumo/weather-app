package com.ngonim.weather.presentation.pages.weather.current

import WeatherAlertDialog
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.ngonim.weather.data.model.GetCurrentWeatherResponse
import com.ngonim.weather.data.model.GetCurrentWeatherResponse.Location
import com.ngonim.weather.data.model.GetForecastResponse
import com.ngonim.weather.presentation.pages.weather.forecast.ForecastDetails
import com.ngonim.weather.util.GenUtil.formatDate


@Composable
fun WeatherDetails(
    current: GetCurrentWeatherResponse,
    forecast: GetForecastResponse?
) {
    var showDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.Center

        ) {

            Text(
                modifier = Modifier.padding(start = 8.dp),
                text = current.location?.name.toString(),
                fontSize = 28.sp,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.width(8.dp))
        }

        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = current.location?.country.toString(),
                fontWeight = FontWeight.Light

            )
            Text(
                text = current.location?.region.toString(),
                color = Color.Gray,
                fontWeight = FontWeight.Light
            )


            Text(text = formatDate(current.location?.localtime.toString()))
        }
        // Spacer(modifier = Modifier.height(16.dp))

        Row {
            Text(
                text = current.current?.tempC.toString(),
                fontSize = 56.sp,
                fontWeight = FontWeight.SemiBold,
                fontStyle = FontStyle.Normal,
                textAlign = TextAlign.Center
            )
            Text(text = "°C", fontWeight = FontWeight.SemiBold)


        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = current.current?.condition?.text.toString(),
            fontSize = 18.sp,
            fontWeight = FontWeight.Light,
            fontStyle = FontStyle.Normal,
            textAlign = TextAlign.Center
        )
        AsyncImage(
            modifier = Modifier.size(160.dp),
            model = "https:${current.current?.condition?.icon}"
                .replace("64x64", "128x128"),
            contentDescription = "Weather Icon"
        )
        Spacer(modifier = Modifier.padding(8.dp))

        ForecastDetails(forecast?.forecast?.forecastday)

        WeatherStats(
            current.current?.windKph.toString(),
            current.current?.humidity.toString(),
            current.current?.precipMm.toString(),
            current.current?.uv.toString(),
            current.current?.windDir.toString(),
            current.current?.visKm.toString()
        )
        Spacer(modifier = Modifier.padding(8.dp))
        Button(
            onClick = { showDialog = true },
            elevation = ButtonDefaults.buttonElevation(
                defaultElevation = 8.dp,
                pressedElevation = 8.dp,
                focusedElevation = 8.dp,
                hoveredElevation = 8.dp,
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {

            Text(text = "More Information")
            if (showDialog) {
                WeatherAlertDialog(
                    onDismiss = { showDialog = false },
                    location = current.location?.name.toString(),
                    temperature = current.current?.tempF.toString(),
                    pressure = current.current?.pressureMb.toString(),
                    heatIndex = current.current?.heatindexC.toString(),
                    lastUpdated = current.current?.lastUpdated.toString()
                )
            }

        }

    }
}

@Preview
@Composable
fun WeatherDetailsPreview() {
    val current = GetCurrentWeatherResponse(
        current = GetCurrentWeatherResponse.Current(
            cloud = 0,
            condition = GetCurrentWeatherResponse.Current.Condition(
                code = 1000,
                icon = "//cdn.weatherapi.com/weather/64x64/day/113.png",
                text = "Sunny"
            ),
            dewpointC = 10.0,
            dewpointF = 50.0,
            feelslikeC = 25.0,
            feelslikeF = 77.0,
            gustKph = 10.0,
            gustMph = 6.2,
            heatindexC = 25.0,
            heatindexF = 77.0,
            humidity = 50,
            isDay = 1,
            lastUpdated = "2023-10-27 10:00",
            lastUpdatedEpoch = 1698380400,
            precipIn = 0.0,
            precipMm = 0.0,
            pressureIn = 30.0,
            pressureMb = 1016.0,
            tempC = 25.0,
            tempF = 77.0,
            uv = 5.0,
            visKm = 10.0,
            visMiles = 6.0,
            windDegree = 180,
            windDir = "S",
            windKph = 5.0,
            windMph = 3.1,
            windchillC = 25.0,
            windchillF = 77.0
        ),
        location = Location(
            country = "Country",
            lat = 0.0,
            localtime = "2023-10-27 10:30",
            localtimeEpoch = 1698382200,
            lon = 0.0,
            name = "City Name",
            region = "Region",
            tzId = "TimezoneID"
        )
    )
    val forecast = null
    WeatherDetails(current = current, forecast = forecast)
}
