package com.example.weatherforecast.ui.auth

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.weatherforecast.navigation.Screen

@Composable
fun AuthRoute(
    authViewModel: LoginViewModel= hiltViewModel(),
    navController: NavController
){
    val uiState by authViewModel.uiSate.collectAsStateWithLifecycle()

    LoginScreen(
        uiState=uiState,
        onLoginClick={ user->
            authViewModel.loginApi(user)
        },
        onLoginSuccess = { response->
            navController.navigate(Screen.Home.route) {
                popUpTo(Screen.Login.route) {   //Cleared Navigation Backstack
                    inclusive = true
                }
            }
        }
    )
}