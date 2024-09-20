package com.example.weatherforecast.data.model

data class WeatherResponse(
    val city: String,
    val list: List<WeatherForecast>
)
//
//data class WeatherForecast(
//    val dt_txt: String,
//    val main: Main,
//    val weather: List<WeatherDescription>
//)
//
//data class Main(
//    val temp: Float,
//    val humidity: Int,
//    val temp_min: Float,
//    val temp_max: Float
//)

data class WeatherDescription(
    val main: String,
    val description: String
)
