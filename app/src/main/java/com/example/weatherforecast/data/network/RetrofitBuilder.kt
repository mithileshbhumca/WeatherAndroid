package com.example.weatherforecast.data.network

import android.content.Context
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

object RetrofitBuilder {

    private const val BASE_URL = "https://api.openweathermap.org/"

    private const val TIMEOUT = 30L
    private const val MAX_RETRY_ATTEMPTS = 2 // Number of retry attempts
    private const val CACHE_SIZE = 10 * 1024 * 1024L // 10 MB cache


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
        // Define cache directory
        val cacheDir = File(context.cacheDir, "http_cache")
        val cache = Cache(cacheDir, CACHE_SIZE)

        return OkHttpClient.Builder()
            .cache(cache) // Set up cache
            .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor) //for logging api info interceptor
            .addInterceptor(RetryInterceptor(MAX_RETRY_ATTEMPTS))// retry api interceptor
            .addInterceptor(NetworkConnectionInterceptor(context))//check internet availability
            .addNetworkInterceptor(CacheInterceptor(context))// add for api response cache
            .build()
    }


    fun getApiService(context: Context): ApiService {
        return getRetrofit(context).create(ApiService::class.java)
    }


}