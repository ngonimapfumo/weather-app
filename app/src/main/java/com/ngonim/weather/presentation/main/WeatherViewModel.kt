package com.ngonim.weather.presentation.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ngonim.weather.data.model.GetAlertsResponse
import com.ngonim.weather.data.model.GetCurrentWeatherResponse
import com.ngonim.weather.data.model.GetForecastResponse
import com.ngonim.weather.data.remote.network.api.RetrofitInstance
import com.ngonim.weather.data.util.NetworkResponse
import com.ngonim.weather.util.Constants
import kotlinx.coroutines.launch

class WeatherViewModel : ViewModel() {
    private val weatherService = RetrofitInstance.weatherApi
    private val _weatherResult = MutableLiveData<NetworkResponse<GetCurrentWeatherResponse>>()
    val weatherResult: LiveData<NetworkResponse<GetCurrentWeatherResponse>> = _weatherResult

    private val _weatherAlertsResult = MutableLiveData<NetworkResponse<GetAlertsResponse>>()
    val weatherAlertsResult: LiveData<NetworkResponse<GetAlertsResponse>> = _weatherAlertsResult

    private val _weatherForecastResult = MutableLiveData<NetworkResponse<GetForecastResponse>>()
    val weatherForecastResult: LiveData<NetworkResponse<GetForecastResponse>> = _weatherForecastResult

    private val cache = mutableMapOf<String, GetCurrentWeatherResponse>()

    fun fetchWeather(query: String) {
        val cached = cache[query]
        _weatherResult.value = NetworkResponse.Loading
        viewModelScope.launch {
            try {
                val response = weatherService.getWeather(Constants.API_KEY, query, "yes")
                if (response.isSuccessful) {
                    response.body()?.let {
                        if (cached != null) {
                            _weatherResult.value = NetworkResponse.Success(cached)
                        }
                        else {
                            _weatherResult.value = NetworkResponse.Success(it)
                        }

                    }
                } else {
                    _weatherResult.value = NetworkResponse.Error("Error fetching weather")
                }
            } catch (e: Exception) {
                Log.d("TAG:Exception", "fetchWeather: ${e.message}")
            }
        }
    }

    fun getAlerts(place: String){
        _weatherAlertsResult.value = NetworkResponse.Loading
        viewModelScope.launch {
            try {
                val response = weatherService.getAlerts(Constants.API_KEY, place)
                if (response.isSuccessful){
                    response.body()?.let {
                        _weatherAlertsResult.value = NetworkResponse.Success(it)
                    }
                }else{
                    _weatherAlertsResult.value = NetworkResponse.Error("Error fetching alerts")
                }
            }catch (e: Exception){
                Log.d("TAG:Exception", "fetchAlerts: ${e.message}")
            }
        }
    }

    fun getForecast(query: String) {
        _weatherForecastResult.value = NetworkResponse.Loading
        viewModelScope.launch {
            try {
                val response = weatherService.getForecast(Constants.API_KEY, query, 6, "no", "no")
                if (response.isSuccessful) {
                    response.body()?.let {
                        _weatherForecastResult.value = NetworkResponse.Success(it)
                    }
                } else {
                    _weatherForecastResult.value = NetworkResponse.Error("Error fetching forecast")
                }
            } catch (e: Exception) {
                Log.d("TAG:Exception", "fetchForecast: ${e.message}")
            }
        }
    }
}