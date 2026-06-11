package com.example.weatherforecast.ui.auth

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.weatherforecast.data.authmodel.LoginResponse
import com.example.weatherforecast.navigation.Screen

@Composable
fun AuthRoute(
    authViewModel: LoginViewModel= hiltViewModel(),
    onLoginSuccess:(LoginResponse)-> Unit,
){
    val uiState by authViewModel.uiSate.collectAsStateWithLifecycle()

    LoginScreen(
        uiState= uiState,
        onLoginClick={ user->
            authViewModel.loginApi(user)
        },
        onLoginSuccess = onLoginSuccess
    )
}