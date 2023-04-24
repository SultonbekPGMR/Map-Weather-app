package com.sultonbek1547.mapweatherapp.utils

import android.app.Activity
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.sultonbek1547.mapweatherapp.models.WeatherData
import com.sultonbek1547.mapweatherapp.retrofit.ApiClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
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