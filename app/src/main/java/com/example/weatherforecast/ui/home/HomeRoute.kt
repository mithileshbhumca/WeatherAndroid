package com.example.weatherforecast.ui.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.weatherforecast.data.model.City

@Composable
fun HomeRoute(
    viewModel: SearchViewModel = hiltViewModel(),
    onCityClick: (City) -> Unit,
    onLogout: () -> Unit
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    HomeScreen(
        uiState = uiState,
        onSearch = { query ->
            viewModel.fetchCity(query)
        },
        onCityClick = onCityClick,
        onLogout = {
            viewModel.logout()
            onLogout()
        }
    )
}