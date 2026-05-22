package com.example.weatherforecast.ui.details.component

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.weatherforecast.data.model.CurrentWeather
import com.example.weatherforecast.utils.Constants.ICON_URL
import com.example.weatherforecast.utils.FormattingUtil
import kotlin.math.roundToInt

@Composable
fun CurrentWeatherCard(
    currentWeather: CurrentWeather,

    modifier: Modifier = Modifier
) {
    val iconUrl = currentWeather.weather?.firstOrNull()?.icon?.let { "$ICON_URL$it.png" }.orEmpty()
    Log.d("iconURL:",iconUrl)
    Card(
        modifier= Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Column(
            modifier=modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            Text(
                text =
                    "${currentWeather.name}, " +
                            "${currentWeather.sys?.country}",
                color = Color.Black,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(
                modifier= Modifier.height(8.dp)
            )
            FormattingUtil.getDateFormatEEE(currentWeather.dt)?.let {
                Text(
                    text = it,
                    color = Color.Gray,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal
                )
            }
            Text(
                text = currentWeather.weather
                    ?.firstOrNull()
                    ?.description
                    ?:""
                ,
                color = Color.Gray,
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal
            )
            Spacer(
                modifier= Modifier.height(8.dp)
            )
            Text(
                text = "${currentWeather.main?.temp?.roundToInt()}°C",
                color = Color.Gray,
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal
            )
            Spacer(
                modifier= Modifier.height(8.dp)
            )
            Row() {

                Text(
                    text = "H:${currentWeather.main?.tempMax?.roundToInt()}°C",
                    color = Color.Gray,
                    fontSize = 14.sp,
                )
                Text(
                    text = "L:${currentWeather.main?.tempMin?.roundToInt()}°C",
                    color = Color.Gray,
                    fontSize = 14.sp,
                )
            }
            Spacer(
                modifier= Modifier.height(8.dp)
            )
            AsyncImage(
                model = iconUrl,
                contentDescription = "weather icon",
                modifier= Modifier.size(100.dp),
                contentScale = ContentScale.Fit
            )
        }
    }

}