package com.example.weatherforecast.domain.usecase

import com.example.weatherforecast.data.model.CurrentWeather
import com.example.weatherforecast.domain.repository.IIWeatherRepository
import retrofit2.Response
import javax.inject.Inject

class GetWeatherUseCase @Inject constructor(
    private val mWeatherRepository: IIWeatherRepository
) {
    suspend fun execute(lat: Double, lon: Double): Response<CurrentWeather> {
        return mWeatherRepository.getCurrentWeather(lat, lon)
    }
}