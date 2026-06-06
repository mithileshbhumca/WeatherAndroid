package com.example.weatherforecast.di

import android.app.Application
import android.content.Context
import com.example.weatherforecast.BuildConfig
import com.example.weatherforecast.data.network.ApiService
import com.example.weatherforecast.data.network.AuthApiService
import com.example.weatherforecast.data.network.interceptor.CacheInterceptor
import com.example.weatherforecast.data.network.interceptor.NetworkConnectionInterceptor
import com.example.weatherforecast.data.network.interceptor.RetryInterceptor
import com.example.weatherforecast.domain.auth.AuthRepository
import com.example.weatherforecast.domain.auth.IIAuthRepository
import com.example.weatherforecast.domain.repository.IIWeatherRepository
import com.example.weatherforecast.domain.repository.WeatherRepository
import com.example.weatherforecast.utils.DefaultDispatcherProvider
import com.example.weatherforecast.utils.DispatcherProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
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
object ApplicationModule {

    @Provides
    @Singleton
    fun provideContext(application: Application): Context {
        return application.applicationContext
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        networkConnectionInterceptor:
        NetworkConnectionInterceptor,
        cacheInterceptor:
        CacheInterceptor,
        context: Context
    ): OkHttpClient {
         val TIMEOUT = 30L
         val MAX_RETRY_ATTEMPTS = 2 // Number of retry attempts
         val CACHE_SIZE = 10 * 1024 * 1024L // 10 MB cache
        val cacheDir = File(context.cacheDir, "http_cache")
        val cache = Cache(cacheDir, CACHE_SIZE)
        return OkHttpClient.Builder()
            .cache(cache) // Set up cache
            .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }) //for logging api info interceptor
            .addInterceptor(RetryInterceptor(MAX_RETRY_ATTEMPTS))// retry api interceptor
            .addInterceptor(networkConnectionInterceptor)//check internet availability
            .addNetworkInterceptor(cacheInterceptor)// add for api response cache
            .build()
    }
    //main retrofit
    @Provides
    @Singleton
    @MainApi
    fun provideMainRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.WEATHER_API_ENDPOINT)
            .client(client)
            .addConverterFactory(
                GsonConverterFactory.create()
            )
            .build()
    }

    @Provides
    @Singleton
    @AuthApi
    fun provideAuthRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.AUTH_API_ENDPOINT)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(
        @MainApi
        retrofit: Retrofit
    ): ApiService {
        return retrofit.create(
            ApiService::class.java
        )
    }
    @Provides
    @Singleton
    fun provideAuthApiService(
        @AuthApi
        retrofit: Retrofit
    ): AuthApiService {
        return retrofit.create(AuthApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideWeatherRepository(apiService: ApiService): IIWeatherRepository {
        return WeatherRepository(apiService)
    }

    @Provides
    @Singleton
    fun provideAuthRepository(authApiService: AuthApiService): IIAuthRepository {
        return AuthRepository(authApiService)
    }

//    @Provides
//    @Singleton
//    fun provideRetrofit(networkBuilder: NetworkBuilder): Retrofit {
//        return networkBuilder.getRetrofit()
//    }
//    @Provides
//    @Singleton
//    fun provideAuthRetrofit(networkBuilder: NetworkBuilder): Retrofit {
//        return networkBuilder.getAuthRetrofit()
//    }

//    @Provides
//    @Singleton
//    fun provideApiService(retrofit: Retrofit): ApiService {
//        return retrofit.create(ApiService::class.java)
//    }

//    @Provides
//    @Singleton
//    fun provideAuthApiService(retrofit: Retrofit): AuthApiService {
//        return retrofit.create(AuthApiService::class.java)
//    }
//    @Provides
//    @Singleton //no need this
//    fun provideWeatherRepository(apiService: ApiService): IIWeatherRepository {
//        return WeatherRepository(apiService)
//    }

//    @Provides
//    @Singleton
//    fun provideNetworkBuilder(
//        networkConnectionInterceptor: NetworkConnectionInterceptor,
//        cacheInterceptor: CacheInterceptor,
//        context: Context
//    ): NetworkBuilder {
//        return NetworkBuilder(networkConnectionInterceptor, cacheInterceptor, context)
//    }
    @Singleton
    @Provides
    fun provideDispatcherProvider(): DispatcherProvider {
        return DefaultDispatcherProvider()
    }
}