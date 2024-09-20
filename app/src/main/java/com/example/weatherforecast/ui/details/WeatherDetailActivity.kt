package com.example.weatherforecast.ui.details

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.weatherforecast.R
import com.example.weatherforecast.data.api.ApiHelperImpl
import com.example.weatherforecast.data.api.RetrofitBuilder
import com.example.weatherforecast.data.api.UiState
import com.example.weatherforecast.data.api.ViewModelFactory
import com.example.weatherforecast.data.model.ThreeHoursWeatherForecast
import com.example.weatherforecast.data.model.WeatherDetailData
import com.example.weatherforecast.data.model.WeatherForecast
import com.example.weatherforecast.databinding.ActivityWeatherDetailBinding
import com.example.weatherforecast.utils.Constants
import java.text.SimpleDateFormat
import java.util.Locale


class WeatherDetailActivity : ComponentActivity() {

    private lateinit var binding: ActivityWeatherDetailBinding

    private lateinit var detailViewModel: WeatherDetailViewModel
    private lateinit var progressBar: ProgressBar
    private lateinit var recyclerView: RecyclerView
    private lateinit var locationText: TextView
    private lateinit var temperatureText: TextView
    private lateinit var maxTempText: TextView
    private lateinit var minTempText: TextView
    private lateinit var weatherIcon: ImageView

    private var forecastAdapter: ForecastAdapter? = null


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
            detailViewModel.fetchDetails(cityLat, cityLog, cityName)
        }
    }

    private fun setupUI() {
        progressBar = binding.progressBar
        recyclerView = binding.forecastRecyclerView

        locationText = binding.location
        temperatureText = binding.temperature
        maxTempText = binding.maxTemp
        minTempText = binding.minTemp
        weatherIcon = binding.weatherIcon

        recyclerView.setLayoutManager(LinearLayoutManager(this));

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

    private fun renderData(weatherDetailData: WeatherDetailData) {
        val cityWeather = weatherDetailData.currentWeather
        val cityForeCast = weatherDetailData.weatherForecast
        locationText.setText(cityWeather.name);
        temperatureText.setText(String.format("%.1f°C", cityWeather.main?.temp));
        maxTempText.setText(String.format("%.1f°C", cityWeather.main?.tempMax));
        minTempText.setText(String.format("%.1f°C", cityWeather.main?.tempMin));
        val icon = cityWeather.weather?.get(0)?.icon
        if (icon != null) {
            loadIcon("https://openweathermap.org/img/w/${icon}.png")
        } else {
            loadIcon("")
        }

        renderForecast(cityForeCast);
    }


    private fun loadIcon(iconUrl: String) {
        if (iconUrl.isNotEmpty()) {
            Glide.with(this).load(iconUrl).into(weatherIcon)
        } else {
            Glide.with(this).load(R.drawable.ic_broken_image).into(weatherIcon)
        }
    }

    private fun renderForecast(cityForeCast: WeatherForecast) {
        val forecastList = getUniqueForecasts(cityForeCast.list!!)

        forecastAdapter = ForecastAdapter(forecastList);
        recyclerView.setAdapter(forecastAdapter);
    }

    private fun getUniqueForecasts(forecastList: List<ThreeHoursWeatherForecast>): List<ThreeHoursWeatherForecast> {
        val uniqueForecasts = ArrayList<ThreeHoursWeatherForecast>()
        val dateMap = HashMap<String, ThreeHoursWeatherForecast>()

        for (item in forecastList) {
            val dateKey =
                SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(item.dt!! * 1000)

            if (!dateMap.containsKey(dateKey)) {
                dateMap[dateKey] = item
            }
        }

        uniqueForecasts.addAll(dateMap.values)
        return uniqueForecasts
    }

}
