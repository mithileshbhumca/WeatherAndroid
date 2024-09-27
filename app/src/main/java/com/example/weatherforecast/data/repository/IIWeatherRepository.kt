package com.example.weatherforecast.data.repository

import com.example.weatherforecast.data.model.City
import com.example.weatherforecast.data.model.CurrentWeather
import com.example.weatherforecast.data.model.WeatherForecast
import com.example.weatherforecast.data.network.RetrofitModule
import retrofit2.Response
import javax.inject.Inject

interface IIWeatherRepository {
    suspend fun getCity(cityAndCountry: String): Response<List<City>>
    suspend fun getWeatherForecast(lat: Double, lon: Double): Response<WeatherForecast>

    suspend fun getCurrentWeather(lat: Double, lon: Double): Response<CurrentWeather>
}