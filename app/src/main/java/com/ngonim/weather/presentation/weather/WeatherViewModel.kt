package com.ngonim.weather.presentation.weather

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ngonim.weather.data.model.GetCurrentWeatherResponse
import com.ngonim.weather.data.model.GetCurrentWeatherResponse.Location
import com.ngonim.weather.data.remote.network.api.RetrofitInstance
import com.ngonim.weather.data.util.NetworkResponse
import com.ngonim.weather.presentation.composables.WeatherDetails
import com.ngonim.weather.util.Constants
import kotlinx.coroutines.launch

class WeatherViewModel : ViewModel() {
    private val weatherService = RetrofitInstance.weatherApi
    private val _weatherResult = MutableLiveData<NetworkResponse<GetCurrentWeatherResponse>>()
    val weatherResult: LiveData<NetworkResponse<GetCurrentWeatherResponse>> = _weatherResult
    fun fetchWeather(city: String) {
        _weatherResult.value = NetworkResponse.Loading
        viewModelScope.launch {
            try {
                val response = weatherService.getWeather(Constants.API_KEY, city, "yes")
                if (response.isSuccessful) {
                    response.body()?.let {
                        _weatherResult.value = NetworkResponse.Success(it)
                    }
                } else {
                    _weatherResult.value = NetworkResponse.Error("Error fetching weather")
                }
            } catch (e: Exception) {
                Log.d("TAG:Exception", "fetchWeather: ${e.message}")
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