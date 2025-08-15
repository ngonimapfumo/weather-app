package com.ngonim.weather.network.api

import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {

    @GET("/v1/current.json")
    suspend fun getWeather(
        @Query("key") apiKey: String,
        @Query("q") city: String
    )
}