package com.ngonim.weather.presentation.pages.weather.current

import WeatherAlertDialog
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
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.ngonim.weather.data.model.GetCurrentWeatherResponse
import com.ngonim.weather.data.model.GetCurrentWeatherResponse.Location
import com.ngonim.weather.util.GenUtil.formatDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


@Composable
fun WeatherDetails(data: GetCurrentWeatherResponse) {
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
                text = data.location?.name.toString(),
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
                text = data.location?.country.toString(),
                fontWeight = FontWeight.Light

            )
            Text(
                text = data.location?.region.toString(),
                color = Color.Gray,
                fontWeight = FontWeight.Light
            )


            Text(text = formatDate(data.location?.localtime.toString()))
        }
         // Spacer(modifier = Modifier.height(16.dp))

        Row {
            Text(
                text = data.current?.tempC.toString(),
                fontSize = 56.sp,
                fontWeight = FontWeight.SemiBold,
                fontStyle = FontStyle.Normal,
                textAlign = TextAlign.Center
            )
            Text(text = "°C",fontWeight = FontWeight.SemiBold)


        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = data.current?.condition?.text.toString(),
            fontSize = 18.sp,
            fontWeight = FontWeight.Light,
            fontStyle = FontStyle.Normal,
            textAlign = TextAlign.Center
        )
        AsyncImage(
            modifier = Modifier.size(160.dp),
            model = "https:${data.current?.condition?.icon}"
                .replace("64x64", "128x128"),
            contentDescription = "Weather Icon"
        )

        Spacer(modifier = Modifier.padding(8.dp))
        WeatherStats(
            data.current?.windKph.toString(),
            data.current?.humidity.toString(),
            data.current?.precipMm.toString(),
            data.current?.uv.toString(),
             data.current?.windDir.toString(),
            data.current?.visKm.toString())

        Button(onClick = { showDialog = true},
            elevation = ButtonDefaults.buttonElevation(
                defaultElevation = 8.dp,
                pressedElevation = 8.dp,
                focusedElevation = 8.dp,
                hoveredElevation = 8.dp,),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)) {

            Text(text = "More Information")
            if (showDialog) {
                WeatherAlertDialog(onDismiss = { showDialog = false },
                    location = data.location?.name.toString(),
                    temperature = data.current?.tempF.toString(),
                    pressure = data.current?.pressureMb.toString(),
                   heatIndex =  data.current?.heatindexC.toString(),
                    lastUpdated = data.current?.lastUpdated.toString())
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