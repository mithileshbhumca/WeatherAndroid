package com.example.weatherforecast.data.network

import com.example.weatherforecast.data.local.TokenManager
import com.example.weatherforecast.domain.usecase.AuthUseCase
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject

//token manage make thread safe only one thread can call refresh token api
class TokenAuthenticator @Inject constructor(
    private val tokenManager: TokenManager,
    private val authUseCase: AuthUseCase
) : Authenticator {
    override fun authenticate(
        route: Route?,
        response: Response
    ): Request? {
        // Prevent infinite loop
        if (responseCount(response) >= 2) {
            return null
        }
        // refresh token
        synchronized(this) {
            val currentToken=tokenManager.getAccessToken()
            val requestToken = response.request.header("Authorization")?.removePrefix("Bearer ")
            // Another thread already refreshed token
            if (currentToken != null && currentToken != requestToken) {
                return response.request.newBuilder()
                    .header("Authorization", "Bearer $currentToken")
                    .build()
            }
            // Refresh token
           val accessToken= fetchNewAccessToken()?:return null

            return response.request.newBuilder()
                .header("Authorization", "Bearer $accessToken")
                .build()

        }
    }

    private fun fetchNewAccessToken(): String? {
        val refreshToken = tokenManager.getRefreshToken() ?: return null
       val response= authUseCase.executeRefreshToken(mapOf("refreshToken" to refreshToken))
        if (!response.isSuccessful) {
            tokenManager.clearTokens()
            return null
        }
        val newAccessToken=response.body()?.accessToken ?: return null
           val expiry = tokenManager.getRefreshExpiry() // already stored

           tokenManager.saveTokens(
               newAccessToken,
               refreshToken,
               (expiry?.minus(System.currentTimeMillis()))?.div(1000) ?: 0
           )

       return newAccessToken
    }
    private fun responseCount(response: Response): Int {
        var result = 1
        var prior = response.priorResponse

        while (prior != null) {
            result++
            prior = prior.priorResponse
        }

        return result
    }
}


/*
With coroutines, Mutex is a clean solution.
private val refreshMutex = Mutex()
refreshMutex.withLock {

    // Another thread may already have refreshed
    if (tokenRepository.accessToken != failedToken) {
        return tokenRepository.accessToken
    }

    val newToken = refreshTokenApi()

    tokenRepository.saveToken(newToken)

    return newToken
}
*/
