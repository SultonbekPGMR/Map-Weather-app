package com.sultonbek1547.mapweatherapp.retrofit

import com.sultonbek1547.mapweatherapp.models.WeatherData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

const val API_KEY = "8a21a95db9bff15ebd4164caa4fcb84f"

interface ApiService {

    @GET("weather")
    suspend fun getWeatherData(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") appId: String = API_KEY,
    ): Response<WeatherData>
}