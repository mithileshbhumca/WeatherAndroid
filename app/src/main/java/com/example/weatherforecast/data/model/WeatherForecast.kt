package com.example.weatherforecast.data.model

import com.google.gson.annotations.SerializedName

data class WeatherForecast(
    @SerializedName("cod") val cod: String? = null,
    @SerializedName("message") val message: Double? = null,
    @SerializedName("cnt") val cnt: Int? = null,
    @SerializedName("list") val list: List<ThreeHoursWeatherForecast>? = null,
    @SerializedName("city") val city: City? = null
)