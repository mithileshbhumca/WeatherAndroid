package com.example.weatherforecast.domain.usecase

import com.example.weatherforecast.data.model.WeatherForecast
import com.example.weatherforecast.domain.repository.IIWeatherRepository
import retrofit2.Response
import javax.inject.Inject

class GetForecastUseCase @Inject constructor(
    private val mWeatherRepository: IIWeatherRepository
) {
    suspend fun execute(lat: Double, lon: Double): Response<WeatherForecast> {
        return mWeatherRepository.getWeatherForecast(lat, lon)
    }
}