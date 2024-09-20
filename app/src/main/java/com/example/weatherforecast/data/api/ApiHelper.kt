package com.example.weatherforecast.data.api

import com.example.weatherforecast.data.model.City
import com.example.weatherforecast.data.model.CurrentWeather
import com.example.weatherforecast.data.model.WeatherForecast

interface ApiHelper {

//    suspend fun getUsers(): List<ApiUser>


//    fun getCurrentWeather(cityAndCountry: String): CurrentWeather?
//    fun getWeatherForecast(cityAndCountry: String): WeatherForecast?

    suspend fun getCity(cityAndCountry: String): List<City>
    suspend fun getWeatherForecast(lat: Double, lon: Double): WeatherForecast

}