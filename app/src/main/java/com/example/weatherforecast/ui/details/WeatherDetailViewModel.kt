package com.example.weatherforecast.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherforecast.data.api.ApiHelper
import com.example.weatherforecast.data.api.ApiHelperImpl
import com.example.weatherforecast.data.api.UiState
import com.example.weatherforecast.data.model.City
import com.example.weatherforecast.data.model.WeatherForecast
import kotlinx.coroutines.launch

class WeatherDetailViewModel(
    private val apiHelper: ApiHelper
) : ViewModel() {
    private val uiState = MutableLiveData<UiState<WeatherForecast>>()

    init {
        // fetchCity()
    }

    fun fetchDetails(lat: Double, lon: Double) {
        viewModelScope.launch {
            uiState.postValue(UiState.Loading)
            try {
                val usersFromApi = apiHelper.getWeatherForecast(lat, lon)
                uiState.postValue(UiState.Success(usersFromApi))
            } catch (e: Exception) {
                uiState.postValue(UiState.Error(e.toString()))
            }
        }
    }

    fun getUiState(): LiveData<UiState<WeatherForecast>> {
        return uiState
    }

}
