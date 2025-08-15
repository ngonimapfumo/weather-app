package com.ngonim.weather.data

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ngonim.weather.network.api.RetrofitInstance
import kotlinx.coroutines.launch

class WeatherViewModel : ViewModel() {
    private val weatherService = RetrofitInstance.weatherApi
    fun fetchWeather(city: String) {
        viewModelScope.launch {
            val response = weatherService.getWeather("d4544ccacad044fcb2d172633252307", city,"no")

            if (response.isSuccessful) {
                Log.d("TAG:Response", "fetchWeather: ${response.body().toString()}")
            } else {
                Log.d("TAG:Response", "fetchWeather: ${response.message()}")
            }
        }
    }
}