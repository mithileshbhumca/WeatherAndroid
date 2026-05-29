package com.example.weatherforecast.domain.repository

import com.example.weatherforecast.data.model.City
import com.example.weatherforecast.data.model.CurrentWeather
import com.example.weatherforecast.data.model.WeatherForecast
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface IIWeatherRepository {
    fun getCity(cityAndCountry: String): Flow<Response<List<City>>>
    fun getWeatherForecast(lat: Double, lon: Double): Flow<Response<WeatherForecast>>
    fun getCurrentWeather(lat: Double, lon: Double): Flow<Response<CurrentWeather>>
}