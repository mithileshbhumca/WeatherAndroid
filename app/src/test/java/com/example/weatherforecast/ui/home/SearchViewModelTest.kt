package com.example.weatherforecast.ui.home

import app.cash.turbine.test
import com.example.weatherforecast.data.model.City
import com.example.weatherforecast.domain.repository.UiState
import com.example.weatherforecast.domain.usecase.GetCityUseCase
import com.example.weatherforecast.ui.utils.TestDispatcherProvider
import com.example.weatherforecast.utils.DispatcherProvider
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import retrofit2.Response

@ExperimentalCoroutinesApi
class SearchViewModelTest {

    @MockK
    private lateinit var getCityUseCase: GetCityUseCase

    private lateinit var viewModel: SearchViewModel
    private lateinit var testDispatcherProvider: DispatcherProvider

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        testDispatcherProvider = TestDispatcherProvider()
        viewModel = SearchViewModel(getCityUseCase, testDispatcherProvider)
    }

    @Test
    fun fetchCitySuccess() = runTest {
        val cities = listOf(City(1, "Paris", null, "FR", null, 2.0, 48.0))
        val response = Response.success(cities)

        coEvery { getCityUseCase.execute("Paris") } returns flowOf(response)

        viewModel.uiState.test {
            viewModel.fetchCity("Paris")
            advanceUntilIdle() //Let the coroutine finish everything
            assertEquals(UiState.Idle, awaitItem())
            assertEquals(UiState.Success(cities), awaitItem())
            cancelAndConsumeRemainingEvents() //Cancel Flow collection and clean up
        }
    }

    @Test
    fun `fetchCity emits Loading and Error on null body`() = runTest {
        val response = Response.success<List<City>>(null)

        coEvery { getCityUseCase.execute("Paris") } returns flowOf(response)

        viewModel.uiState.test {
            viewModel.fetchCity("Paris")
            advanceUntilIdle()

            assertEquals(UiState.Idle, awaitItem())
            assertEquals(UiState.Error("No cities found"), awaitItem())
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `fetchCity emits Loading and Error on exception`() = runTest {
        coEvery { getCityUseCase.execute("Paris") } returns flow { throw RuntimeException("Server error") }

        viewModel.uiState.test {
            viewModel.fetchCity("Paris")
            advanceUntilIdle()
            assertEquals(UiState.Idle, awaitItem())
            val error = awaitItem() as UiState.Error
            assertTrue(error.message.contains("Server error"))
            cancelAndConsumeRemainingEvents()
        }
    }


    @After
    fun tearDown() {
        // do something if required
    }


}
