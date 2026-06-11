package com.example.weatherforecast.data.network

import com.example.weatherforecast.data.authmodel.LoginResponse
import com.example.weatherforecast.data.authmodel.RefreshResponse
import com.example.weatherforecast.data.authmodel.User
import com.example.weatherforecast.data.model.City
import com.example.weatherforecast.data.model.CurrentWeather
import com.example.weatherforecast.data.model.WeatherForecast
import com.example.weatherforecast.data.model.WeatherResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface AuthApiService {

    @POST("login")
    suspend fun login(@Body body: User): Response<LoginResponse>


    @POST("signup")
    suspend fun signup(@Body body: User): Response<Map<String, Any>>

    @POST("refresh")
    fun refreshToken(@Body body: Map<String, String>): Response<RefreshResponse>

}