package com.example.weatherforecast.data.network

import com.example.weatherforecast.data.authmodel.AuthResponse
import com.example.weatherforecast.data.authmodel.RefreshResponse
import com.example.weatherforecast.data.authmodel.User
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface AuthApiService {

    @POST("login")
    suspend fun login(@Body body: User): Response<AuthResponse>


    @POST("signup")
    suspend fun signup(@Body body: User): Response<AuthResponse>

    @POST("refresh")
    fun refreshToken(@Body body: Map<String, String>): Response<RefreshResponse>

}