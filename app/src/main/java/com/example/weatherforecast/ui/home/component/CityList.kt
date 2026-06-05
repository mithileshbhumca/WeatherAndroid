package com.example.weatherforecast.ui.home.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.weatherforecast.data.model.City
import com.example.weatherforecast.data.model.Coord

//this replace recycler view
@Composable
fun CityList(
    cities: List<City>,
    onCityClick: (City) -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = contentPadding,
        verticalArrangement = Arrangement.spacedBy(8.dp)
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

@Preview
@Composable
fun WeatherPreview() {
   val data= listOf(
        City(
            name = "Noida",
            state = "UP",
            country = "India",
            lat = 28.57,
            lon = 77.32,
            coord= Coord(8.57, 77.32,)
        ),
        City(
            name = "Delhi",
            state = "Delhi",
            country = "India",
            lat = 28.61,
            lon = 77.20,
            coord= Coord(28.61, 77.20,)

        )
    )
    CityList(
        data,
        onCityClick = {},
    )
}