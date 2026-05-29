package com.example.weatherforecast.ui.details.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weatherforecast.R
import com.example.weatherforecast.data.model.ThreeHoursWeatherForecast
import com.example.weatherforecast.data.model.WeatherForecast
import com.example.weatherforecast.utils.FormattingUtil

@Composable
fun CityForecastCard(
    weatherForecast: WeatherForecast
) {
    val forecastList = weatherForecast.list ?: emptyList()
    val uniqueForecastList=if(forecastList.isNotEmpty())getUniqueForecasts(forecastList) else emptyList()

    Card(
        modifier= Modifier
            .fillMaxWidth()
            .padding(16.dp),
        elevation =
            CardDefaults.cardElevation(
                defaultElevation = 6.dp
            )
    ){
        Column(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            Text(
                text = stringResource(R.string.forecast_5days),
                color = Color.Black,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(16.dp))
            uniqueForecastList.forEach { item->
                ForecastItem(item)
            }
        }

    }

}



private fun getUniqueForecasts(forecastList: List<ThreeHoursWeatherForecast>): List<ThreeHoursWeatherForecast> {
    val uniqueForecasts = ArrayList<ThreeHoursWeatherForecast>()
    val dateMap = LinkedHashMap<String?, ThreeHoursWeatherForecast>()

    for (item in forecastList) {
        val dateKey = FormattingUtil.getFormatDate(item.dt)
        if (!dateMap.containsKey(dateKey)) {
            dateMap[dateKey] = item
        }
    }

    uniqueForecasts.addAll(dateMap.values)
    return uniqueForecasts
}