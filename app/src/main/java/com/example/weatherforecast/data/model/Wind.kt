
package com.example.weatherforecast.data.model

import com.google.gson.annotations.SerializedName

data class Wind(
        @SerializedName("speed") val speed: Double?,
        @SerializedName("deg") val deg: Double?)