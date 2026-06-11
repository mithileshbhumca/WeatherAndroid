package com.example.weatherforecast.navigation

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.weatherforecast.ui.auth.AuthRoute
import com.example.weatherforecast.ui.auth.LoginViewModel
import com.example.weatherforecast.ui.details.WeatherDetailRoute
import com.example.weatherforecast.ui.home.HomeRoute

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun AppNavigation(
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val navController = rememberNavController()

    val startDestination = remember {
        if (viewModel.isLoggedIn()) Screen.Home.route else Screen.Login.route
    }

    SharedTransitionLayout(modifier = modifier) {
        NavHost(
            navController = navController,
            startDestination = startDestination
        ) {
            composable (route = Screen.Login.route){
                AuthRoute(
                   onLoginSuccess = {
                       navController.navigate(Screen.Home.route){
                           popUpTo(Screen.Login.route){   //Cleared Navigation Backstack
                               inclusive=true
                           }
                       }
                   }
                )
            }
            composable(route= Screen.Home.route) {
                HomeRoute(
                    onCityClick = {city->
                        navController.navigate(
                            Screen.WeatherDetails.createRoute(
                                city.lat?:0.0,
                                city.lon?:0.0
                            )
                        )
                    },
                    onLogout = {
                        navController.navigate(Screen.Login.route) {
                            popUpTo(Screen.Home.route) { inclusive = true }
                        }
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
                    lat = lat,
                    lon = lon,
                    onBackClick = {
                        navController.popBackStack()
                    }
                )


            }

        }

    }

}