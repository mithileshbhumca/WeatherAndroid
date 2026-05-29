package com.example.weatherforecast.domain.usecase

import com.example.weatherforecast.data.model.City
import com.example.weatherforecast.domain.repository.IIWeatherRepository
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Inject

class GetCityUseCase @Inject constructor(
    private val mWeatherRepository: IIWeatherRepository
) {
    fun execute(cityAndCountry: String): Flow<Response<List<City>>> {
        return mWeatherRepository.getCity(cityAndCountry)
    }
}