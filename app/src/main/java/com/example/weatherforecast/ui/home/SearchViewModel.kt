package com.example.weatherforecast.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherforecast.data.model.City
import com.example.weatherforecast.data.network.NoConnectivityException
import com.example.weatherforecast.domain.repository.UiState
import com.example.weatherforecast.domain.usecase.GetCityUseCase
import com.example.weatherforecast.utils.DispatcherProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val mGetCityUseCase: GetCityUseCase,
    private val dispatcherProvider: DispatcherProvider
) : ViewModel() {
    private val _uiState = MutableStateFlow<UiState<List<City>>>(UiState.Idle)
    val uiState: StateFlow<UiState<List<City>>> = _uiState

    fun fetchCity(cityName: String) {
        viewModelScope.launch(dispatcherProvider.main) {
            try {
                mGetCityUseCase.execute(cityName)
                    .flowOn(dispatcherProvider.io)
                    .onStart { _uiState.value = UiState.Loading } // Cleaner loading emission
                    .catch { e ->
                        _uiState.value = UiState.Error(e.toString())
                    }
                    .collect { response ->
                        if (response.isSuccessful && response.body() != null) {
                            _uiState.value = UiState.Success(response.body()!!)
                        } else {
                            _uiState.value = UiState.Error("No cities found")
                        }
                    }
            } catch (e: NoConnectivityException) {
                _uiState.value = UiState.Error(e.message.toString())
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.toString())
            }
        }
    }

}
