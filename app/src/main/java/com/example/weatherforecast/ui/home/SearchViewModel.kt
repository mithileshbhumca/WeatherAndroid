package com.example.weatherforecast.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherforecast.domain.repository.UiState
import com.example.weatherforecast.data.model.City
import com.example.weatherforecast.data.network.NoConnectivityException
import com.example.weatherforecast.domain.usecase.GetCityUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel  @Inject constructor(
    private val mGetCityUseCase: GetCityUseCase
) : ViewModel() {
    private val uiState = MutableLiveData<UiState<List<City>>>()

    fun fetchCity(cityName: String) {
        viewModelScope.launch {
            uiState.postValue(UiState.Loading)
            try {
                val response = mGetCityUseCase.execute(cityName)
                if (response.isSuccessful && response.body() != null) {
                    uiState.postValue(UiState.Success(response.body()!!))
                } else {
                    uiState.postValue(UiState.Error("No cities found"))
                }
            } catch (e: NoConnectivityException) {
                uiState.postValue(UiState.Error(e.message.toString()))
            } catch (e: Exception) {
                uiState.postValue(UiState.Error(e.toString()))
            }
        }
    }

    fun getUiState(): LiveData<UiState<List<City>>> {
        return uiState
    }

}
