package com.example.weatherforecast.domain.usecase

import com.example.weatherforecast.data.authmodel.AuthResponse
import com.example.weatherforecast.data.authmodel.RefreshResponse
import com.example.weatherforecast.data.authmodel.User
import com.example.weatherforecast.domain.auth.IIAuthRepository
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Inject

class AuthUseCase @Inject constructor(
    private val mAuthRepository: IIAuthRepository
) {
    fun executeLogin(userInfo: User): Flow<Response<AuthResponse>>{
        return mAuthRepository.getSignIn(userInfo)
    }
    fun executeSignUp(userInfo: User): Flow<Response<AuthResponse>>{
        return mAuthRepository.getSignUp(userInfo)
    }

    fun executeRefreshToken(refreshToken: Map<String, String>): Response<RefreshResponse>{
        return mAuthRepository.getRefreshToken(refreshToken)
    }
}