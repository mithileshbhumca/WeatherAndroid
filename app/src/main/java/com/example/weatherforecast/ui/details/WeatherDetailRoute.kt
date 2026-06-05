package com.example.weatherforecast.ui.details

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun WeatherDetailRoute(
    lat: Double,
    lon: Double,
    onBackClick: () -> Unit = {},
    viewModel: WeatherDetailViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState
        .collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.fetchDetails(lat, lon)
    }

    WeatherDetailScreen(
        uiState = uiState,
        onBackClick = onBackClick
    )
}