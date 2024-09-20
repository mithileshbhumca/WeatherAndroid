package me.amitshekhar.learn.kotlin.coroutines.data.api

import com.example.weatherforecast.data.model.City
import com.example.weatherforecast.data.model.CurrentWeather
import com.example.weatherforecast.data.model.WeatherForecast
import com.example.weatherforecast.data.model.WeatherResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

//    @GET("data/2.5/forecast")
//    suspend fun getWeatherForecast(
//        @Query("q") cityName: String,
//        @Query("appid") apiKey: String,
//        @Query("units") units: String = "metric"
//    ): WeatherResponse


    @GET("data/2.5/weather")
    fun getCurrentWeather(
        @Query("q") cityName: String,
        @Query("units") units: String,
        @Query("appid") appId: String
    ): Call<CurrentWeather>

    // Docs: https://openweathermap.org/forecast5
    // Example: http://api.openweathermap.org/data/2.5/forecast?q=London,uk&units=metric&appid=f04391f2a7b156421675d08ac24dc908

    @GET("geo/1.0/direct")
    suspend fun getCities(
        @Query("q") cityName: String,
        @Query("limit") limit: Int = 5,
        @Query("appid") apiKey: String
    ): List<City>

    @GET("data/2.5/forecast")
    fun getWeatherForecast(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("units") units: String,
        @Query("appid") apiKey: String
    ): WeatherForecast


}