package com.example.weatherforecast.data.api

import com.example.weatherforecast.BuildConfig
import com.example.weatherforecast.data.model.City
import com.example.weatherforecast.data.model.CurrentWeather
import com.example.weatherforecast.data.model.WeatherForecast
import me.amitshekhar.learn.kotlin.coroutines.data.api.ApiService

class ApiHelperImpl(private val apiService: ApiService) : ApiHelper {
    //
//    override suspend fun getUsers() = apiService.getUsers()
    companion object {
        private const val UNITS = "metric"
    }
//
//    override fun getCurrentWeather(cityAndCountry: String): CurrentWeather? {
//        return apiService
//            .getCurrentWeather(cityAndCountry, UNITS, BuildConfig.WEATHER_API_APP_ID)
//            .execute()
//            .body()
//    }
//
//    override fun getWeatherForecast(cityAndCountry: String): WeatherForecast? {
//        return apiService
//            .getWeatherForecast(cityAndCountry, UNITS, BuildConfig.WEATHER_API_APP_ID)
//            .execute()
//            .body()
//    }

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

}