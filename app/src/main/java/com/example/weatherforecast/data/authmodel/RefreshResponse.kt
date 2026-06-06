package com.example.weatherforecast.data.authmodel

data class RefreshResponse(
    val accessToken: String,
    val refreshToken: String,
    val refreshTokenExpiry: Long // add this
)