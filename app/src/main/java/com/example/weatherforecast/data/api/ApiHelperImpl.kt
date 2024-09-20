package com.example.weatherforecast.data.api

import com.example.weatherforecast.BuildConfig
import com.example.weatherforecast.data.model.City
import com.example.weatherforecast.data.model.CurrentWeather
import com.example.weatherforecast.data.model.WeatherForecast

class ApiHelperImpl(private val apiService: ApiService) : ApiHelper {

    companion object {
        private const val UNITS = "metric"
    }
    override suspend fun getCity(cityAndCountry: String): List<City> {
        return apiService.getCities(cityAndCountry, apiKey = BuildConfig.WEATHER_API_APP_ID)
    }

    override suspend fun getWeatherForecast(lat: Double,lon:Double): WeatherForecast {
        return apiService.getWeatherForecast(
            lat,
            lon,
            UNITS,
            apiKey = BuildConfig.WEATHER_API_APP_ID
        )
    }


    override suspend fun getCurrentWeather(cityAndCountry: String): CurrentWeather {
        return apiService.getCurrentWeather(cityAndCountry, UNITS, BuildConfig.WEATHER_API_APP_ID)

    }

}