package com.ngonim.weather.data.remote.network.api

import com.ngonim.weather.data.model.GetCurrentWeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {

    @GET("/v1/current.json")
    suspend fun getWeather(
        @Query("key") apiKey: String,
        @Query("q") city: String,
        @Query("aqi") airQuality: String
    ): Response<GetCurrentWeatherResponse>

    @GET("")
    suspend fun getForecast()

}