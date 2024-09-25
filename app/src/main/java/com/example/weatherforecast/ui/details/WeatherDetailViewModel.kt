package com.example.weatherforecast.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherforecast.data.network.ApiHelper
import com.example.weatherforecast.data.repository.UiState
import com.example.weatherforecast.data.model.WeatherDetailData
import com.example.weatherforecast.data.network.NoConnectivityException
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class WeatherDetailViewModel(
    private val apiHelper: ApiHelper
) : ViewModel() {
    private val uiState = MutableLiveData<UiState<WeatherDetailData>>()

    fun fetchDetails(lat: Double, lon: Double, city: String?) {
        viewModelScope.launch {
            uiState.postValue(UiState.Loading)

            try {
                coroutineScope {

                    val currentWeatherResponse = async { apiHelper.getCurrentWeather(lat, lon) }

                    val forecastResponse =
                        async { apiHelper.getWeatherForecast(lat, lon) }

                    val getCurrentWeather = currentWeatherResponse.await()

                    val getWeatherForecast = forecastResponse.await()

                    if (getCurrentWeather.isSuccessful && getCurrentWeather.body() != null && getWeatherForecast.isSuccessful && getWeatherForecast.body() != null) {
                        val weatherDetailData =
                            WeatherDetailData(
                                getCurrentWeather.body()!!,
                                getWeatherForecast.body()!!
                            )
                        uiState.postValue(UiState.Success(weatherDetailData))

                    } else {
                        uiState.postValue(UiState.Error("Error fetching weather data"))
                    }
                }
            } catch (e: NoConnectivityException) {
                uiState.postValue(UiState.Error(e.message.toString()))
            } catch (e: Exception) {
                uiState.postValue(UiState.Error(e.toString()))
            }
        }
    }

    fun getUiState(): LiveData<UiState<WeatherDetailData>> {
        return uiState
    }

}
