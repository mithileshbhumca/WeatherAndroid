package com.example.weatherforecast.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherforecast.data.model.WeatherDetailData
import com.example.weatherforecast.data.network.NoConnectivityException
import com.example.weatherforecast.domain.UiState
import com.example.weatherforecast.domain.usecase.GetForecastUseCase
import com.example.weatherforecast.domain.usecase.GetWeatherUseCase
import com.example.weatherforecast.utils.DispatcherProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherDetailViewModel @Inject constructor(
    private val mGetWeatherUseCase: GetWeatherUseCase,
    private val mGetForecastUseCase: GetForecastUseCase,
    private val dispatcherProvider: DispatcherProvider
) : ViewModel() {
    private val _uiState = MutableStateFlow<UiState<WeatherDetailData>>(UiState.Loading)
    val uiState: StateFlow<UiState<WeatherDetailData>> = _uiState.asStateFlow()

    fun fetchDetails(lat: Double, lon: Double) {
        viewModelScope.launch(dispatcherProvider.main) {
            _uiState.value = UiState.Loading

            try {
                combine(
                    mGetWeatherUseCase.execute(lat, lon),
                    mGetForecastUseCase.execute(lat, lon)

                ) { currentWeatherResponse, forecastResponse ->
                    Pair(currentWeatherResponse, forecastResponse)
                }
                    .flowOn(dispatcherProvider.io)
                    .collect { (currentWeatherResponse, forecastResponse) ->
                        if (currentWeatherResponse.isSuccessful && currentWeatherResponse.body() != null && forecastResponse.isSuccessful && forecastResponse.body() != null) {
                            val weatherDetailData =
                                WeatherDetailData(
                                    currentWeatherResponse.body()!!,
                                    forecastResponse.body()!!
                                )
                            _uiState.value = UiState.Success(weatherDetailData)

                        } else {
                            _uiState.value = UiState.Error("Error fetching weather data")
                        }
                    }

            } catch (e: NoConnectivityException) {
                _uiState.value = UiState.Error(e.message.toString())
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.toString())
            }
        }
    }
}
