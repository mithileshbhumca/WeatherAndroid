package com.example.weatherforecast.domain.auth

import com.example.weatherforecast.data.authmodel.AuthResponse
import com.example.weatherforecast.data.authmodel.RefreshResponse
import com.example.weatherforecast.data.authmodel.User
import com.example.weatherforecast.data.model.City
import com.example.weatherforecast.data.model.CurrentWeather
import com.example.weatherforecast.data.model.WeatherForecast
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface IIAuthRepository {
    fun getSignIn(useInfo: User): Flow<Response<AuthResponse>>
    fun getSignUp(useInfo: User): Flow<Response<AuthResponse>>

    fun getRefreshToken(refreshToken: Map<String, String>): Response<RefreshResponse>

}