package com.ngonim.weather.data.remote.network.service

import com.ngonim.weather.data.model.GetCurrentWeatherResponse
import com.ngonim.weather.data.model.GetForecastResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {

    @GET("current.json")
    suspend fun getWeather(
        @Query("key") apiKey:String,
        @Query("q") city:String,
        @Query("aqi") airQuality:String
    ): Response<GetCurrentWeatherResponse>

    @GET("forecast.json")
    suspend fun getForecast(
        @Query("key") apiKey:String,
        @Query("q") city:String,
        @Query("days") days:Int,
        @Query("aqi") airQuality:String,
        @Query("alerts") alerts:String
    ): Response<GetForecastResponse>
}