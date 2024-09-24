package com.example.weatherforecast.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherforecast.data.network.ApiHelper
import com.example.weatherforecast.data.repository.UiState
import com.example.weatherforecast.data.model.City
import kotlinx.coroutines.launch

class SearchViewModel(
    private val apiHelper: ApiHelper
) : ViewModel() {
    private val uiState = MutableLiveData<UiState<List<City>>>()

    fun fetchCity(cityName: String) {
        viewModelScope.launch {
            uiState.postValue(UiState.Loading)
            try {
                val response = apiHelper.getCity(cityName)
                if (response.isSuccessful && response.body() != null) {
                    uiState.postValue(UiState.Success(response.body()!!))
                } else {
                    uiState.postValue(UiState.Error("No cities found"))
                }
            } catch (e: Exception) {
                uiState.postValue(UiState.Error(e.toString()))
            }
        }
    }

    fun getUiState(): LiveData<UiState<List<City>>> {
        return uiState
    }

}
