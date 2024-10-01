package com.example.weatherforecast.ui.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.weatherforecast.domain.repository.UiState
import com.example.weatherforecast.data.model.City
import com.example.weatherforecast.domain.usecase.GetCityUseCase
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
class SearchViewModelTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Mock
    private lateinit var getCityUseCase: GetCityUseCase

    @Mock
    private lateinit var uiStateObserver: Observer<UiState<List<City>>>
    private lateinit var viewModel: SearchViewModel

    @Before
    fun setUp() {
        viewModel = SearchViewModel(getCityUseCase)
    }

    @Test
    fun fetchCitySuccess() {
        testCoroutineRule.runBlockingTest {
            Mockito.doReturn(emptyList<City>())
                .`when`(getCityUseCase)
                .execute("")

            viewModel.getUiState().observeForever(uiStateObserver)
            viewModel.fetchCity("")
            Mockito.verify(getCityUseCase).execute("")
            Mockito.verify(uiStateObserver, Mockito.times(2))
                .onChanged(UiState.Success(Mockito.any()))
        }
    }

    @Test
    fun fetchCitySuccessError() {
        testCoroutineRule.runBlockingTest {
            val errorMessage = "Error Message"
            Mockito.doThrow(RuntimeException(errorMessage))
                .`when`(getCityUseCase)
                .execute("")

            viewModel.getUiState().observeForever(uiStateObserver)
            viewModel.fetchCity("")

            Mockito.verify(getCityUseCase).execute("")
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
