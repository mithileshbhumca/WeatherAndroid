package com.example.weatherforecast.ui.auth

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.weatherforecast.data.authmodel.AuthResponse

@Composable
fun AuthRoute(
    authViewModel: LoginViewModel= hiltViewModel(),
    onAuthSuccess:(AuthResponse)-> Unit,
){
    val uiState by authViewModel.uiSate.collectAsStateWithLifecycle()

    LoginScreen(
        uiState= uiState,
        onLoginClick= { user->
            authViewModel.loginApi(user)
        },
        onAuthSuccess = onAuthSuccess,
        onSignUpClick = { user->
            authViewModel.signUpApi(user)
        },
    )
}