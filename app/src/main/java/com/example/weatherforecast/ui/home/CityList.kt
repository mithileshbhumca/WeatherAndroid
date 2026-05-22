package com.example.weatherforecast.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.example.weatherforecast.data.model.City

//this replace recycler view
@Composable
fun CityList(
    cities: List<City>,
    onCityClick: (City) -> Unit

) {

    LazyColumn(
        verticalArrangement =
            Arrangement.spacedBy(8.dp)

    ) {

        items(cities) { city ->
            CityItem(
                city = city,

                onClick = {

                    onCityClick(city)
                }
            )
        }
    }
}