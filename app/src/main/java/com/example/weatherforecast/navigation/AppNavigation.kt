package com.example.weatherforecast.navigation

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.weatherforecast.ui.details.WeatherDetailRoute
import com.example.weatherforecast.ui.home.HomeRoute

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun AppNavigation(modifier: Modifier= Modifier){
    val navController = rememberNavController()
    SharedTransitionLayout(modifier=modifier){
        NavHost(
            navController=navController,
            startDestination = Screen.Home.route
        ){
            composable(route= Screen.Home.route) {
                HomeRoute(
                    onCityClick = {city->
                        navController.navigate(
                            Screen.WeatherDetails.createRoute(
                                city.lat?:0.0,
                                city.lon?:0.0
                            )
                        )
                    }
                )

            }

            composable (route= Screen.WeatherDetails.route){backStackEntry->
                val lat=backStackEntry.arguments?.getString("lat")
                    ?.toDoubleOrNull()?:0.0
                val lon=backStackEntry.arguments
                    ?.getString("lon")
                    ?.toDoubleOrNull()
                    ?:0.0
                WeatherDetailRoute(
                    lat=lat,
                    lon=lon
                )


            }

        }

    }

}