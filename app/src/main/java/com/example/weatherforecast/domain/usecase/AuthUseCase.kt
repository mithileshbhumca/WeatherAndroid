package com.example.weatherforecast.domain.usecase

import com.example.weatherforecast.data.authmodel.LoginResponse
import com.example.weatherforecast.data.authmodel.User
import com.example.weatherforecast.domain.auth.IIAuthRepository
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Inject

class AuthUseCase @Inject constructor(
    private val mAuthRepository: IIAuthRepository
) {
    fun executeLogin(userInfo: User): Flow<Response<LoginResponse>>{
        return mAuthRepository.getSignIn(userInfo)
    }
    fun executeSignUp(userInfo: User): Flow<Response<Map<String, Any>>>{
        return mAuthRepository.getSignUp(userInfo)
    }
}