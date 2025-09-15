package com.ngonim.weather.presentation.pages.weather.forecast

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.ngonim.weather.data.model.GetForecastResponse
import com.ngonim.weather.util.GenUtil
import kotlin.math.max

@Composable
fun ForecastDetails(forecast: List<GetForecastResponse.Forecast.Forecastday?>?) {
    val days = forecast.orEmpty().filterNotNull()

    // Defensive: compute overall min/max across available days
    val overallMin = days.mapNotNull { it.day?.mintempC }.minOrNull() ?: 0.0
    val overallMax = days.mapNotNull { it.day?.maxtempC }.maxOrNull() ?: (overallMin + 1.0)

    // Debug logging (optional) so you can confirm the size and values in Logcat
    LaunchedEffect(days) {
        // Use Log.d in real app; for quick checks you can println
        println("ForecastDetails: days.size = ${days.size}, min=$overallMin, max=$overallMax")
        days.forEach { d ->
            println(" -> date=${d.date} min=${d.day?.mintempC} max=${d.day?.maxtempC}")
        }
    }

    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp, vertical = 8.dp)) {
        forecast?.take(3)?.forEach { day ->
            ForecastRow(day = day, overallMin = overallMin, overallMax = overallMax)
        }
    }
}

@Composable
private fun ForecastRow(
    day: GetForecastResponse.Forecast.Forecastday?,
    overallMin: Double,
    overallMax: Double
) {
    val dateStr = day!!.date ?: ""
    val min = day.day?.mintempC ?: 0.0
    val max = day.day?.maxtempC ?: 0.0
    val iconUrl = day.day?.condition?.icon

    val range = max(1.0, overallMax - overallMin)
    val progressFraction = ((max - min) / range).toFloat().coerceIn(0f, 1f)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = GenUtil.formatDayAbbreviation(dateStr),
            modifier = Modifier.width(56.dp),
            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(modifier = Modifier.width(8.dp))

        if (!iconUrl.isNullOrBlank()) {
            AsyncImage(
                model = "https:$iconUrl",
                contentDescription = "icon",
                modifier = Modifier.size(28.dp)
            )
        } else {
            Icon(
                imageVector = androidx.compose.material.icons.Icons.Default.WbSunny,
                contentDescription = null,
                tint = Color.Yellow,
                modifier = Modifier.size(24.dp)
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        // Min temp
        Text(
            text = "${min.toInt()}°",
            modifier = Modifier.width(40.dp)
        )

        Spacer(modifier = Modifier.width(8.dp))

        Box(
            modifier = Modifier
                .weight(1f)
                .height(8.dp)
                .clip(RoundedCornerShape(50))
                .background(Color(0x33FFFFFF))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(progressFraction)
                    .clip(RoundedCornerShape(50))
                    .background(Brush.horizontalGradient(listOf(Color(0xFF6DD400), Color(0xFFFFD54F), Color(0xFFFF7043))))
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        // Max temp
        Text(
            text = "${max.toInt()}°",
            modifier = Modifier.width(40.dp)
        )
    }
}

@Preview
@Composable
fun ForecastDetailsPreview() {
    val forecast = listOf(
        GetForecastResponse.Forecast.Forecastday(
            date = "2023-10-26",
            day = GetForecastResponse.Forecast.Forecastday.Day(
                mintempC = 10.0,
                maxtempC = 20.0,
                condition = GetForecastResponse.Forecast.Forecastday.Day.Condition(icon = "//cdn.weatherapi.com/weather/64x64/day/116.png", code = 0, text = ""),
                avghumidity = 0,
                avgtempC = 0.0,
                avgtempF = 0.0,
                avgvisKm = 0.0,
                avgvisMiles = 0.0,
                dailyChanceOfRain = 0,
                dailyChanceOfSnow = 0,
                dailyWillItRain = 0,
                dailyWillItSnow = 0,
                maxtempF = 0.0,
                maxwindKph = 0.0,
                maxwindMph = 0.0,
                mintempF = 0.0,
                totalprecipIn = 0.0,
                totalprecipMm = 0.0,
                totalsnowCm = 0.0,
                uv = 0.0
            ),
            astro = null, dateEpoch = 0, hour = null
        ),
        GetForecastResponse.Forecast.Forecastday(
            date = "2023-10-27",
            day = GetForecastResponse.Forecast.Forecastday.Day(
                mintempC = 12.0,
                maxtempC = 22.0,
                condition = GetForecastResponse.Forecast.Forecastday.Day.Condition(icon = "//cdn.weatherapi.com/weather/64x64/day/119.png", code = 0, text = ""),
                avghumidity = 0, avgtempC = 0.0, avgtempF = 0.0, avgvisKm = 0.0, avgvisMiles = 0.0, dailyChanceOfRain = 0, dailyChanceOfSnow = 0, dailyWillItRain = 0, dailyWillItSnow = 0, maxtempF = 0.0, maxwindKph = 0.0, maxwindMph = 0.0, mintempF = 0.0, totalprecipIn = 0.0, totalprecipMm = 0.0, totalsnowCm = 0.0, uv = 0.0
            ),
            astro = null, dateEpoch = 0, hour = null
        )
    )
    ForecastDetails(forecast = forecast)
}

@Preview
@Composable
fun ForecastRowPreview() {
    val day = GetForecastResponse.Forecast.Forecastday(date = "2023-10-26", day = GetForecastResponse.Forecast.Forecastday.Day(mintempC = 10.0, maxtempC = 20.0, condition = GetForecastResponse.Forecast.Forecastday.Day.Condition(icon = "//cdn.weatherapi.com/weather/64x64/day/116.png", code = 0, text = ""), avghumidity = 0, avgtempC = 0.0, avgtempF = 0.0, avgvisKm = 0.0, avgvisMiles = 0.0, dailyChanceOfRain = 0, dailyChanceOfSnow = 0, dailyWillItRain = 0, dailyWillItSnow = 0, maxtempF = 0.0, maxwindKph = 0.0, maxwindMph = 0.0, mintempF = 0.0, totalprecipIn = 0.0, totalprecipMm = 0.0, totalsnowCm = 0.0, uv = 0.0), astro = null, dateEpoch = 0, hour = null)
    ForecastRow(day = day, overallMin = 5.0, overallMax = 25.0)
}