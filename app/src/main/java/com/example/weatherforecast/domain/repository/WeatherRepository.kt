package com.example.weatherforecast.domain.repository

import com.example.weatherforecast.BuildConfig
import com.example.weatherforecast.data.model.City
import com.example.weatherforecast.data.model.CurrentWeather
import com.example.weatherforecast.data.model.WeatherForecast
import com.example.weatherforecast.data.network.ApiService
import com.example.weatherforecast.data.network.NetworkBuilder
import retrofit2.Response
import javax.inject.Inject

class WeatherRepository @Inject constructor(
    private val networkBuilder: NetworkBuilder
) : IIWeatherRepository {
    companion object {
        private const val UNITS = "metric"
        private const val API_KEY = BuildConfig.WEATHER_API_APP_ID
    }

    override suspend fun getCity(cityAndCountry: String): Response<List<City>> {
        return networkBuilder.getRetrofit().create(ApiService::class.java)
            .getCities(cityAndCountry, apiKey = API_KEY)
    }

    override suspend fun getWeatherForecast(lat: Double, lon: Double): Response<WeatherForecast> {
        return networkBuilder.getRetrofit().create(ApiService::class.java)
            .getWeatherForecast(
                lat,
                lon,
                UNITS,
                apiKey = API_KEY
            )
    }


    override suspend fun getCurrentWeather(lat: Double, lon: Double): Response<CurrentWeather> {
        return networkBuilder.getRetrofit().create(ApiService::class.java)
            .getCurrentWeather(lat, lon, UNITS, API_KEY)
    }
}