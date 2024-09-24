package com.example.weatherforecast.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weatherforecast.data.network.ApiHelper
import com.example.weatherforecast.ui.details.WeatherDetailViewModel
import com.example.weatherforecast.ui.home.SearchViewModel

class ViewModelFactory(private val apiHelper: ApiHelper) :
    ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            (modelClass.isAssignableFrom(SearchViewModel::class.java)) -> {
                SearchViewModel(apiHelper) as T
            }

            (modelClass.isAssignableFrom(WeatherDetailViewModel::class.java)) -> {
                WeatherDetailViewModel(apiHelper) as T
            }

            else -> throw IllegalArgumentException("Unknown ViewModel class")

        }

    }

}