package com.example.weatherforecast.ui.details

import app.cash.turbine.test
import com.example.weatherforecast.data.model.CurrentWeather
import com.example.weatherforecast.data.model.WeatherDetailData
import com.example.weatherforecast.data.model.WeatherForecast
import com.example.weatherforecast.domain.UiState
import com.example.weatherforecast.domain.usecase.GetForecastUseCase
import com.example.weatherforecast.domain.usecase.GetWeatherUseCase
import com.example.weatherforecast.ui.utils.TestDispatcherProvider
import com.example.weatherforecast.utils.DispatcherProvider
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import retrofit2.Response

@ExperimentalCoroutinesApi
class WeatherDetailViewModelTest {

    @MockK
    private lateinit var weatherUseCase: GetWeatherUseCase

    @MockK
    private lateinit var forecastUseCase: GetForecastUseCase

    private lateinit var testDispatcherProvider: DispatcherProvider

    private lateinit var viewModel: WeatherDetailViewModel
    private val lat = 2.0
    private val log = 48.0

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        testDispatcherProvider = TestDispatcherProvider()
        viewModel = WeatherDetailViewModel(weatherUseCase, forecastUseCase, testDispatcherProvider)
    }

    @Test
    fun givenServerResponse200_whenFetch_shouldReturnSuccess() = runTest {

        val mockCurrentWeather = CurrentWeather(name = "CityName", main = null, weather = null)
        val mockWeatherForecast = WeatherForecast(list = emptyList())
        val weatherDetailData = WeatherDetailData(mockCurrentWeather, mockWeatherForecast)

        val resCurrentWeather = Response.success(mockCurrentWeather)
        val resWeatherForecast = Response.success(mockWeatherForecast)

        coEvery { weatherUseCase.execute(lat, log) } returns flowOf(resCurrentWeather)
        coEvery { forecastUseCase.execute(lat, log) } returns flowOf(resWeatherForecast)
        viewModel.uiState.test {
            viewModel.fetchDetails(lat, log)
            advanceUntilIdle()
            assertEquals(UiState.Loading, awaitItem())
            assertEquals(UiState.Success(weatherDetailData), awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }


    @Test
    fun `fetchDetails emits Error when one API response fails`() = runTest {

        val mockCurrentWeather = CurrentWeather(name = "CityName", main = null, weather = null)
        val resCurrentWeather = Response.success(mockCurrentWeather)

        coEvery { weatherUseCase.execute(lat, log) } returns flowOf(resCurrentWeather)
        every { forecastUseCase.execute(lat, log) } returns flow {
            emit(Response.error(404, "Not found".toResponseBody()))
        }
        viewModel.uiState.test {
            viewModel.fetchDetails(lat, log)
            assertEquals(UiState.Loading, awaitItem())
            assertTrue(awaitItem() is UiState.Error)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `fetchDetails emits Error when exception occurs`() = runTest {
        val mockWeatherForecast = WeatherForecast(list = emptyList())
        val resWeatherForecast = Response.success(mockWeatherForecast)

        coEvery { weatherUseCase.execute(lat, log) } returns flow {
            throw RuntimeException("Something went wrong")
        }
        coEvery { forecastUseCase.execute(lat, log) } returns flowOf(resWeatherForecast)
        viewModel.uiState.test {
            viewModel.fetchDetails(lat, log)
            assertEquals(UiState.Loading, awaitItem())
            assertTrue(awaitItem() is UiState.Error)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @After
    fun tearDown() {
        //do something if required
    }
}
