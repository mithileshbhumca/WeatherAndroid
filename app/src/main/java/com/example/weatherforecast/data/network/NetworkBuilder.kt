package com.example.weatherforecast.data.network

import android.content.Context
import com.example.weatherforecast.data.network.interceptor.CacheInterceptor
import com.example.weatherforecast.data.network.interceptor.NetworkConnectionInterceptor
import com.example.weatherforecast.data.network.interceptor.RetryInterceptor
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkBuilder @Inject constructor(
    private val networkConnectionInterceptor: NetworkConnectionInterceptor,
    private val cacheInterceptor: CacheInterceptor,
    private val context: Context
) {
    private val BASE_URL = "https://api.openweathermap.org/"
    private val TIMEOUT = 30L
    private val MAX_RETRY_ATTEMPTS = 2 // Number of retry attempts
    private val CACHE_SIZE = 10 * 1024 * 1024L // 10 MB cache

    private fun getLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }


    private fun getHttpClient(): OkHttpClient {
        // Define cache directory
        val cacheDir = File(context.cacheDir, "http_cache")
        val cache = Cache(cacheDir, CACHE_SIZE)

        return OkHttpClient.Builder()
            .cache(cache) // Set up cache
            .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
            .addInterceptor(getLoggingInterceptor()) //for logging api info interceptor
            .addInterceptor(RetryInterceptor(MAX_RETRY_ATTEMPTS))// retry api interceptor
            .addInterceptor(networkConnectionInterceptor)//check internet availability
            .addNetworkInterceptor(cacheInterceptor)// add for api response cache
            .build()
    }

    fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(getHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

}