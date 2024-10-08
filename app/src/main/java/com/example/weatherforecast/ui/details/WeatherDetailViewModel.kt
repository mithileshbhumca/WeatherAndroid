package com.example.weatherforecast.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherforecast.domain.repository.UiState
import com.example.weatherforecast.data.model.WeatherDetailData
import com.example.weatherforecast.data.network.NoConnectivityException
import com.example.weatherforecast.domain.usecase.GetForecastUseCase
import com.example.weatherforecast.domain.usecase.GetWeatherUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherDetailViewModel @Inject constructor(
    private val mGetWeatherUseCase: GetWeatherUseCase,
    private val mGetForecastUseCase: GetForecastUseCase
) : ViewModel() {
    private val uiState = MutableLiveData<UiState<WeatherDetailData>>()

    fun fetchDetails(lat: Double, lon: Double) {
        viewModelScope.launch {
            uiState.postValue(UiState.Loading)

            try {
                coroutineScope {

                    val currentWeatherResponse = async { mGetWeatherUseCase.execute(lat, lon) }

                    val forecastResponse =
                        async { mGetForecastUseCase.execute(lat, lon) }

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
