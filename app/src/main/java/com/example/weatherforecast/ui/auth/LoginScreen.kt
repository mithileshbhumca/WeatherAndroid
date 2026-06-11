package com.example.weatherforecast.ui.auth

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.weatherforecast.data.authmodel.LoginResponse
import com.example.weatherforecast.data.authmodel.User
import com.example.weatherforecast.domain.UiState
import com.example.weatherforecast.ui.component.WeatherTopAppBar

@Composable
fun LoginScreen(
    uiState: UiState<LoginResponse>,
    modifier: Modifier = Modifier,
    onLoginSuccess: (LoginResponse) -> Unit,
    onLoginClick: (User) -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) }

    LaunchedEffect(uiState) {
        if (uiState is UiState.Success) {
            onLoginSuccess(uiState.data)
        }
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            WeatherTopAppBar(title = "Weather Location")
        }
    ) { paddingValues ->
        LoginUI(
            uiState = uiState,
            modifier = Modifier.padding(paddingValues),
            email = email,
            password = password,
            isError = isError,
            onEmailChange = {
                email = it
                isError = false
            },
            onPasswordChange = {
                password = it
                isError = false
            },
            onLoginClick = { e, p ->
                if (e.isNotEmpty()&&p.isNotEmpty()) {
                    val user = User("", e, p)
                    onLoginClick(user)
                } else {
                    isError = true
                }
            },
            onLoginSuccess
        )

    }
}


@Composable
fun LoginUI(
    uiState: UiState<LoginResponse>,
    modifier: Modifier = Modifier,
    email: String,
    password: String,
    isError: Boolean = false,
    onEmailChange: (String) -> Unit = {},
    onPasswordChange: (String) -> Unit = {},
    onLoginClick: (String, String) -> Unit = { _, _ -> },
    onLoginSuccess: (LoginResponse) -> Unit,
) {

    when (uiState) {
        is UiState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        is UiState.Success -> {
            // Navigation handled in LaunchedEffect above
        }

        is UiState.Error -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = uiState.message,
                    color = MaterialTheme.colorScheme.error
                )
            }

        }

        else -> {}
    }

    Column(
        modifier = modifier
            .padding(24.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = email,
            onValueChange = onEmailChange,
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth(),
            isError = isError,
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email
            )
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = password,
            onValueChange = onPasswordChange,
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth(),
            isError = isError,
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password
            )
        )

        if (isError) {
            Text(
                text = "Invalid email or password",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier
                    .padding(top = 4.dp)
                    .align(Alignment.Start)
            )
        }

        Spacer(modifier = Modifier.weight(1f))
        Button(
            onClick = { onLoginClick(email, password) },
            modifier = Modifier.fillMaxWidth()

        ) {
            Text(
                text = "Login",
                style = MaterialTheme.typography.titleMedium
            )
        }


    }
}


@Preview(showBackground = true)
@Composable
fun PreviewLoginScreen() {
    // LoginScreen()
}




