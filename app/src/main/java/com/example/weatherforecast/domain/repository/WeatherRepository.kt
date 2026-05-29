package com.example.weatherforecast.domain.repository

import com.example.weatherforecast.BuildConfig
import com.example.weatherforecast.data.network.ApiService
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class WeatherRepository @Inject constructor(
    private val apiService: ApiService
) : IIWeatherRepository {
    companion object {
        private const val UNITS = "metric"
        private const val API_KEY = BuildConfig.WEATHER_API_APP_ID
    }

    override fun getCity(cityAndCountry: String) = flow {
        emit(apiService.getCities(cityAndCountry, apiKey = API_KEY))
    }

    override fun getWeatherForecast(lat: Double, lon: Double) = flow {
        emit(
            apiService.getWeatherForecast(
                lat,
                lon,
                UNITS,
                apiKey = API_KEY
            )
        )
    }


    override fun getCurrentWeather(lat: Double, lon: Double) = flow {
        emit(apiService.getCurrentWeather(lat, lon, UNITS, API_KEY))
    }
}