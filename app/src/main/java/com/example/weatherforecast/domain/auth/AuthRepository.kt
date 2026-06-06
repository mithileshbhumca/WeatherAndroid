package com.example.weatherforecast.domain.auth

import com.example.weatherforecast.data.authmodel.User
import com.example.weatherforecast.data.network.AuthApiService
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val authApiService: AuthApiService
): IIAuthRepository {

    override fun getSignIn(useInfo: User)= flow {
        emit(authApiService.login(
            body = useInfo
        ))
    }

    override fun getSignUp(useInfo: User)=flow {
        emit(authApiService.signup(
            body = useInfo
        ))
    }
}