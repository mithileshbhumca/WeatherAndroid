package com.example.weatherforecast.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherforecast.data.api.ApiHelper
import com.example.weatherforecast.data.api.ApiHelperImpl
import com.example.weatherforecast.data.api.UiState
import com.example.weatherforecast.data.model.City
import com.example.weatherforecast.data.model.WeatherDetailData
import com.example.weatherforecast.data.model.WeatherForecast
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class WeatherDetailViewModel(
    private val apiHelper: ApiHelper
) : ViewModel() {
    private val uiState = MutableLiveData<UiState<WeatherDetailData>>()

    init {
        // fetchCity()
    }

    fun fetchDetails(lat: Double, lon: Double, city: String?) {
        viewModelScope.launch {
            uiState.postValue(UiState.Loading)

            try {
                coroutineScope {
                    val getWeatherForecastDeffered =
                        async { apiHelper.getWeatherForecast(lat, lon) }
                    val getCurrentWeatherDeffered = async { apiHelper.getCurrentWeather(city!!) }

                    val getWeatherForecast = getWeatherForecastDeffered.await()
                    val getCurrentWeather = getCurrentWeatherDeffered.await()

                    val weatherDetailData = WeatherDetailData(getCurrentWeather, getWeatherForecast)
                    uiState.postValue(UiState.Success(weatherDetailData))

                }

            } catch (e: Exception) {
                uiState.postValue(UiState.Error(e.toString()))
            }
        }
    }

    fun getUiState(): LiveData<UiState<WeatherDetailData>> {
        return uiState
    }

}
