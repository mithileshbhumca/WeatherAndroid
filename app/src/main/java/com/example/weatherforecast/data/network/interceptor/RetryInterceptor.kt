package com.example.weatherforecast.data.network.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class RetryInterceptor(private val maxRetryAttempts: Int) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var attempt = 0
        var response: Response? = null
        var exception: IOException? = null

        while (attempt < maxRetryAttempts) {
            try {
                response = chain.proceed(chain.request())
                // If the request is successful, return the response
                if (response.isSuccessful) {
                    return response
                }
            } catch (e: IOException) {
                exception = e
            }

            attempt++
        }

        // If all retry attempts failed, throw the last exception
        throw exception ?: IOException("Unknown error occurred")
    }
}
