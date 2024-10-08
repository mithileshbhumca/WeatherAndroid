package com.example.weatherforecast.ui.details

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.weatherforecast.domain.repository.UiState
import com.example.weatherforecast.data.model.CurrentWeather
import com.example.weatherforecast.data.model.WeatherDetailData
import com.example.weatherforecast.data.model.WeatherForecast
import com.example.weatherforecast.domain.usecase.GetForecastUseCase
import com.example.weatherforecast.domain.usecase.GetWeatherUseCase
import com.example.weatherforecast.utils.TestCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.*
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class WeatherDetailViewModelTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Mock
    private lateinit var weatherUseCase: GetWeatherUseCase

    @Mock
    private lateinit var forecastUseCase: GetForecastUseCase

    @Mock
    private lateinit var uiStateObserver: Observer<UiState<WeatherDetailData>>
    private lateinit var viewModel: WeatherDetailViewModel

    @Before
    fun setUp() {
        viewModel = WeatherDetailViewModel(weatherUseCase, forecastUseCase)
    }

    @Test
    fun fetchDetails_shouldReturnSuccess() {
        val mockCurrentWeather = CurrentWeather(name = "CityName", main = null, weather = null)
        val mockWeatherForecast = WeatherForecast(list = emptyList())

        testCoroutineRule.runBlockingTest {
            Mockito.doReturn(mockCurrentWeather)
                .`when`(weatherUseCase)
                .execute(0.0, 0.0)

            Mockito.doReturn(mockWeatherForecast)
                .`when`(forecastUseCase)
                .execute(0.0, 0.0)

            viewModel.getUiState().observeForever(uiStateObserver)
            viewModel.fetchDetails(0.0, 0.0)
            Mockito.verify(weatherUseCase).execute(0.0, 0.0)
            Mockito.verify(forecastUseCase).execute(0.0, 0.0)

            Mockito.verify(uiStateObserver, Mockito.times(2))
                .onChanged(UiState.Success(Mockito.any()))
        }
    }

    @Test
    fun fetchDetails_SuccessError() {
        testCoroutineRule.runBlockingTest {
            val errorMessage = "Error Message"

            Mockito.doThrow(RuntimeException(errorMessage))
                .`when`(weatherUseCase)
                .execute(0.0, 0.0)

            viewModel.getUiState().observeForever(uiStateObserver)
            viewModel.fetchDetails(0.0, 0.0)

            Mockito.verify(uiStateObserver).onChanged(
                UiState.Error(RuntimeException(errorMessage).toString())
            )
        }
    }

    @After
    fun tearDown() {
        viewModel.getUiState().removeObserver(uiStateObserver)
    }
}
