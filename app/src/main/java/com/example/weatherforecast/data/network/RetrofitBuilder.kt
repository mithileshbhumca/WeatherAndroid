package com.example.weatherforecast.data.network

import android.content.Context
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitBuilder {

    private const val BASE_URL = "https://api.openweathermap.org/"

    private const val TIMEOUT = 30L
    private const val MAX_RETRY_ATTEMPTS = 2 // Number of retry attempts


    private fun getRetrofit(context: Context): Retrofit {
        val okHttpClient = getHttpClient(context)
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    }

    // Create a logging interceptor
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY // Use BODY for detailed logging
    }

    private fun getHttpClient(context: Context): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .addInterceptor(RetryInterceptor(MAX_RETRY_ATTEMPTS))
            .addInterceptor(NetworkConnectionInterceptor(context))
            .build()
    }


    fun getApiService(context: Context): ApiService {
        return getRetrofit(context).create(ApiService::class.java)
    }


}