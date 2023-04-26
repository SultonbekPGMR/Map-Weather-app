package com.sultonbek1547.mapweatherapp

import android.location.Geocoder
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.sultonbek1547.mapweatherapp.databinding.ActivityWeatherInfoActivtyBinding
import com.sultonbek1547.mapweatherapp.models.WeatherData
import com.sultonbek1547.mapweatherapp.utils.getStatusImage
import com.sultonbek1547.mapweatherapp.utils.getWeatherData
import kotlinx.coroutines.*
import java.text.SimpleDateFormat
import java.util.*

class WeatherInfoActivity : AppCompatActivity() {
    private val binding by lazy { ActivityWeatherInfoActivtyBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val latitude = intent.getDoubleExtra("latitude", 34.0522)
        val longitude = intent.getDoubleExtra("longitude", 118.2437)
        val geocoder = Geocoder(this, Locale.getDefault())
        val address = geocoder.getFromLocation(latitude, longitude, 1)


        CoroutineScope(Dispatchers.IO).launch {
            val weatherDataDeferred: Deferred<WeatherData?> = async {
                getWeatherData(latitude, longitude)
            }
            val weatherData = weatherDataDeferred.await()

            withContext(Dispatchers.Main) {
                binding.apply {
                    tvTemperature.text =
                        (weatherData?.main?.temp?.minus(273.15))?.toInt().toString()
                    tvWind.text = weatherData?.wind?.speed.toString()
                    address?.let { tvCityName.text = it[0].countryName }
                    tvLocation.text = weatherData?.name

                    imgStatus.setImageResource(getStatusImage(weatherData?.weather?.first()))

                    tvTime.text = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date())

                    mainLayout.visibility = View.VISIBLE
                    progressBar.visibility = View.GONE

                }
            }

        }

    }

    override fun onBackPressed() {
        finish()
    }
}