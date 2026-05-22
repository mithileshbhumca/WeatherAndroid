package com.example.weatherforecast.ui.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weatherforecast.R
import com.example.weatherforecast.data.model.City
import com.example.weatherforecast.data.model.Coord
import com.example.weatherforecast.domain.repository.UiState
import com.example.weatherforecast.ui.home.component.CityList
import com.example.weatherforecast.ui.home.component.SearchBarContent
import com.example.weatherforecast.ui.theme.WeatherForecastTheme
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    uiState: UiState<List<City>>,

    onSearch: (String) -> Unit,

    onCityClick: (City) -> Unit,

    modifier: Modifier = Modifier
){
    ScreenContent(uiState,onSearch,onCityClick,modifier)
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenContent(
    uiState: UiState<List<City>>,
    onSearch: (String) -> Unit,
    onCityClick: (City) -> Unit,
    modifier: Modifier = Modifier

){
    var query by rememberSaveable {
        mutableStateOf("")
    }
    var expanded by rememberSaveable {
        mutableStateOf(false)
    }
    LaunchedEffect(query) {

        if (query.length >= 3) {

            delay(300)

            onSearch(query)
        }
    }
    Scaffold(
        modifier = modifier.fillMaxSize(),

        topBar = {
            TopAppBar(
                title = {
                    Text("Weather location")
                },
                colors =
                    TopAppBarDefaults.topAppBarColors(
                        containerColor =
                            MaterialTheme.colorScheme.primary,
                        titleContentColor = MaterialTheme.colorScheme.onSecondary
                    )

            )
        }
    ){ innerPadding ->
        Column(
            modifier = Modifier
                .padding(
                    innerPadding
                )
                .fillMaxSize()
                .padding(horizontal = 16.dp)

        ) {
            Text(stringResource(R.string.welcome_to_weather_app), fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier= Modifier.padding(top = 16.dp)
            )
            Spacer(Modifier.height(8.dp))
            Text(stringResource(R.string.type_any_city_name),
                color=Color.Gray,
                fontSize = 16.sp
                )
            Spacer(Modifier.height(20.dp))

            SearchBarContent(
                query = query,
                expanded = expanded,
                onQueryChange = {
                    query = it
                },
                onExpandedChange = {
                    expanded = it
                },
                searchResults = when (uiState) {

                    is UiState.Success ->
                        uiState.data

                    else -> emptyList()
                },
                onSearch = {

                    onSearch(query)

                    expanded = false
                },
                onResultClick = { city ->

                    expanded = false

                    onCityClick(city)
                }
            )

            Spacer(
                modifier = Modifier.height(16.dp)
            )

            when (uiState) {

                is UiState.Loading -> {

                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment =
                            Alignment.Center

                    ) {
                        CircularProgressIndicator()
                    }
                }

                is UiState.Error -> {

                    Box(

                        modifier = Modifier.fillMaxSize(),

                        contentAlignment = Alignment.Center
                    ) {

                        Text(

                            text = uiState.message,

                            color =
                                MaterialTheme
                                    .colorScheme
                                    .error
                        )
                    }
                }

                is UiState.Success -> {

                    CityList(

                        cities = uiState.data,

                        onCityClick = onCityClick
                    )
                }

                else -> Unit
            }

        }
    }

}




@Preview
@Composable
fun WeatherScreenGeneratingPreview() {
    WeatherForecastTheme() {
        ScreenContent(

            uiState = UiState.Success(

                listOf(
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
            ),

            onSearch = {},

            onCityClick = {}
        )
    }
}