package com.example.weatherforecast.domain.usecase

import com.example.weatherforecast.data.model.CurrentWeather
import com.example.weatherforecast.domain.repository.IIWeatherRepository
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Inject

class GetWeatherUseCase @Inject constructor(
    private val mWeatherRepository: IIWeatherRepository
) {
    fun execute(lat: Double, lon: Double): Flow<Response<CurrentWeather>> {
        return mWeatherRepository.getCurrentWeather(lat, lon)
    }
}