package com.example.weatherforecast.ui.auth

import android.os.Parcelable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.weatherforecast.data.authmodel.AuthResponse
import com.example.weatherforecast.data.authmodel.User
import com.example.weatherforecast.domain.UiState
import com.example.weatherforecast.ui.component.WeatherTopAppBar
import kotlinx.parcelize.Parcelize

enum class AuthMode {
    LOGIN,
    SIGNUP
}
@Parcelize
data class AuthFormState(
    val email: String = "",
    val name: String = "",
    val password: String = ""
): Parcelable
@Composable
fun LoginScreen(
    uiState: UiState<AuthResponse>,
    modifier: Modifier = Modifier,
    onAuthSuccess: (AuthResponse) -> Unit,
    onLoginClick: (User) -> Unit,
    onSignUpClick:(User) -> Unit
) {
    var authMode by rememberSaveable {
        mutableStateOf(AuthMode.LOGIN)
    }
    var formState by rememberSaveable {
        mutableStateOf(AuthFormState())
    }
    var isError by rememberSaveable { mutableStateOf(false) }


    LaunchedEffect(uiState) {
        if (uiState is UiState.Success) {
            onAuthSuccess(uiState.data)
        }
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            WeatherTopAppBar(
                title = if(authMode== AuthMode.LOGIN) "Login" else "Create Account"
            )
        }
    ) { padding ->
        Box(
            modifier= Modifier.fillMaxSize().padding(padding)
        ){
            AuthContent(
                authMode=authMode,
                formState = formState,
                isError = isError,
                onFormChange= {
                    formState=it
                    isError= false
                },
                onSubmit = {
                    when(authMode){
                        AuthMode.LOGIN ->{
                            if(formState.email.isBlank() || formState.password.isBlank()){
                                isError=true
                                return@AuthContent
                            }
                            onLoginClick(
                                User(
                                    name = "",
                                    email = formState.email,
                                    password = formState.password
                                )
                            )
                        }
                        AuthMode.SIGNUP -> {
                            if(formState.email.isBlank()||
                                formState.name.isBlank() ||
                                formState.password.isBlank()
                                ){
                                isError=true
                                return@AuthContent
                            }
                            onSignUpClick(
                                User(
                                    name = formState.name,
                                    email = formState.email,
                                    password = formState.password
                                )
                            )

                        }
                    }
                },
                onModeToggle = {
                    authMode =when(authMode){
                        AuthMode.LOGIN-> AuthMode.SIGNUP
                        AuthMode.SIGNUP-> AuthMode.LOGIN
                    }
                    isError=false
                }
            )
            if (uiState is UiState.Loading){
                CircularProgressIndicator(modifier= Modifier.align(Alignment.Center))
            }
            if(uiState is UiState.Error){
                Text(
                    text = uiState.message,
                    color = MaterialTheme.colorScheme.error,
                    modifier= Modifier.align(Alignment.BottomCenter).padding(16.dp)
                )
            }
        }
    }
}


@Composable
fun AuthContent(
    authMode: AuthMode,
    formState: AuthFormState,
    isError: Boolean = false,
    onFormChange:(AuthFormState)-> Unit,
    onSubmit: ()->Unit,
    onModeToggle: ()->Unit
) {
    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {
        OutlinedTextField(
            value = formState.email,
            onValueChange = { onFormChange(formState.copy(email = it))},
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth(),
            isError = isError,
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email
            )
        )
        if(authMode== AuthMode.SIGNUP){
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = formState.name,
                onValueChange = {
                    onFormChange(formState.copy(name = it))
                },
                label = { Text("Name") },
                modifier = Modifier.fillMaxWidth(),
                isError = isError,
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text
                )
            )
        }
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = formState.password,
            onValueChange = {
                onFormChange(formState.copy(password = it))
            },
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
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = if(authMode == AuthMode.LOGIN)
                         "Please enter email and password"
                       else "Please fill all field"
                ,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier
                    .padding(top = 4.dp)
                    .align(Alignment.Start)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = onSubmit,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                if(authMode == AuthMode.LOGIN) "Login"
                else "Create Account"
            )
        }
        Spacer(modifier = Modifier.height(12.dp))
        TextButton(
            onClick = onModeToggle,
            modifier = Modifier.align(Alignment.CenterHorizontally)

        ) {
           Text (
               if(authMode== AuthMode.LOGIN)
                "Don't have account? Sign Up"
            else
                "Already have account? Login"
           )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewLoginScreen() {
    LoginScreen(
        uiState = UiState.Idle,
        onLoginClick = {
        },
        modifier = Modifier,
        onAuthSuccess = {},
        onSignUpClick = {}
    )
}




