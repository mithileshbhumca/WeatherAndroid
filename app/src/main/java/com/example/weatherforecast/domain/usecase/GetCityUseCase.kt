package com.example.weatherforecast.domain.usecase

import com.example.weatherforecast.data.model.City
import com.example.weatherforecast.domain.repository.IIWeatherRepository
import retrofit2.Response
import javax.inject.Inject

class GetCityUseCase @Inject constructor(
    private val mWeatherRepository: IIWeatherRepository
) {
    suspend fun execute(cityAndCountry: String): Response<List<City>> {
        return mWeatherRepository.getCity(cityAndCountry)
    }
}