package com.sultonbek1547.mapweatherapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.sultonbek1547.mapweatherapp.databinding.ActivityMapsBinding
import com.sultonbek1547.mapweatherapp.models.WeatherData
import com.sultonbek1547.mapweatherapp.utils.getWeatherData
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.mapType = GoogleMap.MAP_TYPE_HYBRID




        mMap.setOnMapClickListener { latLng ->
            CoroutineScope(IO).launch {
                val weatherDataDeferred: Deferred<WeatherData?> = async {
                    getWeatherData(latLng.latitude, latLng.longitude)
                }
            }

            val intent = Intent(this, WeatherInfoActivity::class.java).apply {
                putExtra("latitude",latLng.latitude)
                putExtra("longitude",latLng.longitude)
            }
            startActivity(intent)

        }

    }
}