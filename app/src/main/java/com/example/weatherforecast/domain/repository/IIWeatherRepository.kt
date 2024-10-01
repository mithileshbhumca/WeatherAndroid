package com.example.weatherforecast.domain.repository

import com.example.weatherforecast.data.model.City
import com.example.weatherforecast.data.model.CurrentWeather
import com.example.weatherforecast.data.model.WeatherForecast
import retrofit2.Response

interface IIWeatherRepository {
    suspend fun getCity(cityAndCountry: String): Response<List<City>>
    suspend fun getWeatherForecast(lat: Double, lon: Double): Response<WeatherForecast>
    suspend fun getCurrentWeather(lat: Double, lon: Double): Response<CurrentWeather>
}