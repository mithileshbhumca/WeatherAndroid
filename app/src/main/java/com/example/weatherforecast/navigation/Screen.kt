package com.example.weatherforecast.navigation

sealed class Screen(val route: String) {
    data object Login: Screen("login")
    data object SignUp: Screen("signup")
    data object Home : Screen("home")

    data object WeatherDetails : Screen("weather_details/{lat}/{lon}") {
        fun createRoute(
            lat: Double,
            lon: Double
        ): String {
            return "weather_details/$lat/$lon"
        }
    }

}