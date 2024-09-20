package com.example.weatherforecast.data.model

data class WeatherResponse(
    val city: String,
    val list: List<WeatherForecast>
)

data class WeatherDescription(
    val main: String,
    val description: String
)
