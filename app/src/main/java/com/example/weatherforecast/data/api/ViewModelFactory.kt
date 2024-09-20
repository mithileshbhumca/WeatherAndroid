package com.example.weatherforecast.data.api

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weatherforecast.ui.details.WeatherDetailViewModel
import com.example.weatherforecast.ui.home.SearchViewModel

class ViewModelFactory(private val apiHelper: ApiHelper) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchViewModel::class.java)) {
            return SearchViewModel(apiHelper) as T
        }
        if (modelClass.isAssignableFrom(WeatherDetailViewModel::class.java)) {
            return WeatherDetailViewModel(apiHelper) as T
        }

        throw IllegalArgumentException("Unknown class name")
    }

}