package com.example.weatherforecast.data.authmodel

data class AuthResponse(
    val success: Boolean,
    val message: String,
    val accessToken: String? = null,
    val refreshToken: String? = null,
    val refreshTokenExpiry: Long? = null // seconds (optional)
)