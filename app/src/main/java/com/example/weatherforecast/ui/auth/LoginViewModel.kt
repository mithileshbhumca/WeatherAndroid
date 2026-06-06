package com.example.weatherforecast.ui.auth

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherforecast.data.authmodel.LoginResponse
import com.example.weatherforecast.data.authmodel.User
import com.example.weatherforecast.data.local.TokenManager
import com.example.weatherforecast.data.network.NoConnectivityException
import com.example.weatherforecast.domain.UiState
import com.example.weatherforecast.domain.usecase.AuthUseCase
import com.example.weatherforecast.utils.DispatcherProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authUseCase: AuthUseCase,
    private val dispatcherProvider: DispatcherProvider,
    private val tokenManager: TokenManager
): ViewModel() {
    private val _uiState= MutableStateFlow<UiState<LoginResponse>>(UiState.Idle)
    val uiSate = _uiState.asStateFlow()


    fun loginApi(userInfo: User){
        viewModelScope.launch(dispatcherProvider.main) {
            try {
                authUseCase.executeLogin(userInfo)
                    .flowOn(dispatcherProvider.io)
                    .onStart { _uiState.value = UiState.Loading } // Cleaner loading emission
                    .catch { e ->
                        _uiState.value = UiState.Error(e.toString()?:"Unknown Error")
                    }
                    .collect {  response ->
                        if(response.isSuccessful && response.body()!=null){
                            val loginResponse = response.body()!!
                            loginResponse.accessToken?.let { access ->
                                loginResponse.refreshToken?.let { refresh ->
                                    tokenManager.saveTokens(access, refresh)
                                }
                            }
                            _uiState.value= UiState.Success(loginResponse)
                        }else{
                            _uiState.value= UiState.Error("Login API server error")
                        }

                    }

            }catch (e: NoConnectivityException) {
                _uiState.value = UiState.Error(e.message.toString())
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.toString())
            }

        }
    }

    fun isLoginValid(email:String,pass:String): Boolean{
        Log.d("email:",email)
        Log.d("pass:",pass)

        return email.isNotEmpty() && pass.isNotEmpty()
    }

    fun isLoggedIn(): Boolean = tokenManager.getAccessToken() != null


}