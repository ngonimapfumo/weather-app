package com.ngonim.weather.network.api

import com.ngonim.weather.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitInstance {
    private const val BASE_URL = "https://api.weatherapi.com"
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = when {
            BuildConfig.DEBUG -> {
                HttpLoggingInterceptor.Level.BODY
            }

            else -> {
                HttpLoggingInterceptor.Level.NONE
            }
        }

    }
    private val client = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .readTimeout(1, TimeUnit.MINUTES)
        .connectTimeout(1, TimeUnit.MINUTES)
        .build()

    private fun getInstance(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    }

    val weatherApi: WeatherService = getInstance().create(WeatherService::class.java)
}