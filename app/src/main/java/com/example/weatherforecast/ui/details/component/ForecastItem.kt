package com.example.weatherforecast.ui.details.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.HorizontalDivider
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

import com.example.weatherforecast.data.model.ThreeHoursWeatherForecast
import com.example.weatherforecast.utils.Constants
import com.example.weatherforecast.utils.FormattingUtil
import kotlin.math.roundToInt

@Composable
fun ForecastItem(
    item: ThreeHoursWeatherForecast
) {
    Row(
        modifier = Modifier.fillMaxWidth()
            .padding(vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column() {
            FormattingUtil.getDateFormatEEE(item.dt)?.let {
                Text(
                    text = it,
                    fontWeight = FontWeight.Bold
                )
            }

            Text(
                text = "H:${item.main?.tempMax?.roundToInt()}°C",
                color = Color.Gray,
                fontSize = 14.sp,
            )
            Text(
                text = "L:${item.main?.tempMin?.roundToInt()}°C",
                color = Color.Gray,
                fontSize = 14.sp,
            )

        }
        val icon = item.weather?.firstOrNull()?.icon.orEmpty()
        val iconUrl = "${Constants.ICON_URL}$icon.png"
        AsyncImage(
            model = iconUrl,
            contentDescription = "weather icon",
            modifier= Modifier.size(45.dp),
            contentScale = ContentScale.Fit
        )

    }
    HorizontalDivider()
}
