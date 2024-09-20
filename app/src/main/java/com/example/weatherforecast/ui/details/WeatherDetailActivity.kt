package com.example.weatherforecast.ui.details

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.weatherforecast.data.api.ApiHelperImpl
import com.example.weatherforecast.data.api.UiState
import com.example.weatherforecast.data.api.ViewModelFactory
import com.example.weatherforecast.data.model.City
import com.example.weatherforecast.data.model.WeatherForecast
import com.example.weatherforecast.databinding.ActivityWeatherDetailBinding
import com.example.weatherforecast.ui.home.SearchViewModel
import com.example.weatherforecast.utils.Constants
import me.amitshekhar.learn.kotlin.coroutines.data.api.RetrofitBuilder


class WeatherDetailActivity : ComponentActivity() {

    private lateinit var binding: ActivityWeatherDetailBinding

    //private lateinit var weatherApiService: WeatherApiService
    private lateinit var detailViewModel: WeatherDetailViewModel
    private lateinit var progressBar: ProgressBar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWeatherDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupUI()
        setupViewModel()
        setupObserver()

        // Get city name from the Intent
        val cityLat = intent.getDoubleExtra(Constants.CITY_LAT, 0.0)
        val cityLog = intent.getDoubleExtra(Constants.CITY_LOG, 0.0)
        val cityName = intent.getStringExtra(Constants.CITY_NAME)
        if (cityLat != 0.0 && cityLog != 0.0) {
            //binding.cityNameTextView.text = cityName
            //fetchWeatherDetails(cityName)
            detailViewModel.fetchDetails(cityLat, cityLog)
        }
    }

    private fun setupUI() {
        progressBar = binding.progressBar
    }

    private fun setupViewModel() {
        detailViewModel = ViewModelProvider(
            this,
            ViewModelFactory(
                ApiHelperImpl(RetrofitBuilder.apiService)
            )
        )[WeatherDetailViewModel::class.java]
    }

    private fun setupObserver() {
        detailViewModel.getUiState().observe(this) {
            when (it) {
                is UiState.Success -> {
                    progressBar.visibility = View.GONE
                    renderData(it.data)
                    // citiesRecyclerView.visibility = View.VISIBLE
                }

                is UiState.Loading -> {
                    progressBar.visibility = View.VISIBLE
                    // citiesRecyclerView.visibility = View.GONE
                }

                is UiState.Error -> {
                    //Handle Error
                    progressBar.visibility = View.GONE
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun renderData(forecast: WeatherForecast) {

        binding.cityNameTextView.text = forecast.city?.name

    }

}
