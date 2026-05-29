package com.example.weatherforecast.data.model

import com.google.gson.annotations.SerializedName

data class CurrentWeather(
        @SerializedName("coord") val coord: Coord? = null,
        @SerializedName("weather") val weather: List<Weather>? = null,
        @SerializedName("base") val base: String? = null,
        @SerializedName("main") val main: Main? = null,
        @SerializedName("wind") val wind: Wind? = null,
        @SerializedName("clouds") val clouds: Clouds? = null,
        @SerializedName("rain") val rain: Rain? = null,
        @SerializedName("dt") val dt: Long? = 0L,
        @SerializedName("sys") val sys: Sys? = null,
        @SerializedName("id") val id: Int? = 0,
        @SerializedName("name") val name: String? = null,
        @SerializedName("cod") val cod: Int? = 0
)