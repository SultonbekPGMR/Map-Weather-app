package com.sultonbek1547.mapweatherapp

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.sultonbek1547.mapweatherapp.databinding.ActivityWeatherInfoActivtyBinding
import com.sultonbek1547.mapweatherapp.models.WeatherData
import com.sultonbek1547.mapweatherapp.utils.getWeatherData
import kotlinx.coroutines.*

class WeatherInfoActivity : AppCompatActivity() {
    private val binding by lazy { ActivityWeatherInfoActivtyBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val latitude = intent.getDoubleExtra("latitude", 34.0522)
        val longitude = intent.getDoubleExtra("longitude", 118.2437)

        CoroutineScope(Dispatchers.IO).launch {
            val weatherDataDeferred: Deferred<WeatherData?> = async {
                getWeatherData(latitude, longitude)
            }
            val weatherData = weatherDataDeferred.await()

            withContext(Dispatchers.Main) {
                binding.apply {
                    tvCityName.text = weatherData?.name
                    tvTemperature.text = (weatherData?.main?.temp?.minus(273.15))?.toInt().toString()
                    tvTime.text = weatherData?.timezone.toString()
                    tvWind.text = weatherData?.wind.toString()

                    mainLayout.visibility  = View.VISIBLE
                    progressBar.visibility = View.GONE

                }
            }

        }

    }

    override fun onBackPressed() {
        finish()
    }
}