package com.sultonbek1547.mapweatherapp.utils

import android.app.Activity
import android.widget.Toast
import com.sultonbek1547.mapweatherapp.R
import com.sultonbek1547.mapweatherapp.models.Weather
import com.sultonbek1547.mapweatherapp.models.WeatherData
import com.sultonbek1547.mapweatherapp.retrofit.ApiClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

//open class MyUtils : AppCompatActivity() {}

fun Activity.showToast(toastMessage: String) {
    Toast.makeText(this, toastMessage, Toast.LENGTH_SHORT).show()
}

private val apiService = ApiClient.getApiService()

suspend fun getWeatherData(lat: Double, lon: Double): WeatherData? {
    return withContext(Dispatchers.IO) {
        val response = ApiClient.getApiService().getWeatherData(lat, lon)
        response.body()
    }
}

fun getStatusImage(weather: Weather?): Int {
    if (weather != null) {
        return when (weather.icon){
            "01d" -> R.drawable.sun // clear sky day
            "02d" -> R.drawable.cloud_only // few clouds day
            "03d", "03n", "04d", "04n" -> R.drawable.cloudy // scattered clouds, broken clouds
            "09d", "09n", "10d", "10n" -> R.drawable.rain // rain, shower rain
            "11d", "11n" -> R.drawable.lightning // thunderstorm
            "13d", "13n" -> R.drawable.snowy // snow
            "50d", "50n" -> R.drawable.fog // mist
            else -> {R.drawable.sun}
        }

    }
    return R.drawable.sun
}