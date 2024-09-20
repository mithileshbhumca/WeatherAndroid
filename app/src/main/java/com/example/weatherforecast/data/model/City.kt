package com.example.weatherforecast.data.model


import com.google.gson.annotations.SerializedName

data class City(
    @SerializedName("id") val id: Int?,
    @SerializedName("name") val name: String?,
    @SerializedName("coord") val coord: Coord?,
    @SerializedName("country") val country: String?,
    @SerializedName("state") val state: String?,
    @SerializedName("lon") val lon: Double?,
    @SerializedName("lat") val lat: Double?
)