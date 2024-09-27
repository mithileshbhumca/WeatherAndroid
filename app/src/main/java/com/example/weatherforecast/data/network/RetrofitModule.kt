package com.example.weatherforecast.data.network

import android.content.Context
import com.example.weatherforecast.data.network.interceptor.CacheInterceptor
import com.example.weatherforecast.data.network.interceptor.NetworkConnectionInterceptor
import com.example.weatherforecast.data.network.interceptor.RetryInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    private const val BASE_URL = "https://api.openweathermap.org/"

    private const val TIMEOUT = 30L
    private const val MAX_RETRY_ATTEMPTS = 2 // Number of retry attempts
    private const val CACHE_SIZE = 10 * 1024 * 1024L // 10 MB cache

    private fun getLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        @ApplicationContext context: Context,
    ): OkHttpClient {
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
            .addInterceptor(NetworkConnectionInterceptor(context))//check internet availability
            .addNetworkInterceptor(CacheInterceptor(context))// add for api response cache
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        // val okHttpClient = getHttpClient(context)
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
        //return getRetrofit(context).create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideApiHelper(apiService: ApiService): ApiHelper {
        return ApiHelperImpl(apiService)
    }
}