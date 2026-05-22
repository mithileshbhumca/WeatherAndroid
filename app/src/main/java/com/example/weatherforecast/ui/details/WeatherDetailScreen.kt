package com.example.weatherforecast.ui.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.weatherforecast.data.model.CurrentWeather
import com.example.weatherforecast.data.model.WeatherDetailData
import com.example.weatherforecast.data.model.WeatherForecast
import com.example.weatherforecast.domain.repository.UiState
import com.example.weatherforecast.ui.details.component.CityForecastCard
import com.example.weatherforecast.ui.details.component.CurrentWeatherCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherDetailScreen(
    uiState: UiState<WeatherDetailData>,
    modifier: Modifier = Modifier

) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text("Weather Details")
                },
                colors =
                    TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        titleContentColor = MaterialTheme.colorScheme.onSecondary
                    )

            )
        }
    ) { innerPadding ->
        when (uiState) {
            is UiState.Loading -> {
                Box(
                    modifier = modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    contentAlignment = Alignment.Center

                ) {
                    CircularProgressIndicator()
                }

            }

            is UiState.Error -> {
                Box(
                    modifier = modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = uiState.message,
                        color = MaterialTheme.colorScheme.error
                    )
                }

            }

            is UiState.Success -> {
                DetailScreen(
                    weatherDetailData = uiState.data,
                    modifier = Modifier.padding(innerPadding)
                )

            }

            else -> Unit
        }

    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    weatherDetailData: WeatherDetailData,
    modifier: Modifier = Modifier
) {
    val currentWeather = weatherDetailData.currentWeather
    val cityForeCast = weatherDetailData.weatherForecast

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF87CEEB),
                        Color(0xFFE0F7FA)
                    )
                )
            ),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    )
    {
        item {
            CurrentWeatherCard(currentWeather)
        }
        item {
            CityForecastCard(cityForeCast)
        }
    }


}


@Preview
@Composable
fun WeatherDetailScreenGeneratingPreview() {
    val weatherData = WeatherDetailData(CurrentWeather(), WeatherForecast())
    DetailScreen(weatherData)
}