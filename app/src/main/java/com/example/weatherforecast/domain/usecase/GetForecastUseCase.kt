package com.example.weatherforecast.domain.usecase

import com.example.weatherforecast.data.model.WeatherForecast
import com.example.weatherforecast.domain.repository.IIWeatherRepository
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Inject

class GetForecastUseCase @Inject constructor(
    private val mWeatherRepository: IIWeatherRepository
) {
    fun execute(lat: Double, lon: Double): Flow<Response<WeatherForecast>> {
        return mWeatherRepository.getWeatherForecast(lat, lon)
    }
}