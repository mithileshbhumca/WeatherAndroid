package com.example.weatherforecast.di

import android.app.Application
import android.content.Context
import com.example.weatherforecast.data.network.ApiService
import com.example.weatherforecast.data.network.NetworkBuilder
import com.example.weatherforecast.data.network.interceptor.CacheInterceptor
import com.example.weatherforecast.data.network.interceptor.NetworkConnectionInterceptor
import com.example.weatherforecast.domain.repository.IIWeatherRepository
import com.example.weatherforecast.domain.repository.WeatherRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule {

    @Provides
    @Singleton
    fun provideContext(application: Application): Context {
        return application.applicationContext
    }

    @Provides
    @Singleton
    fun provideRetrofit(networkBuilder: NetworkBuilder): Retrofit {
        return networkBuilder.getRetrofit()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    @Singleton //no need this
    fun provideWeatherRepository(apiService: ApiService): IIWeatherRepository {
        return WeatherRepository(apiService)
    }

    @Provides
    @Singleton
    fun provideNetworkBuilder(
        networkConnectionInterceptor: NetworkConnectionInterceptor,
        cacheInterceptor: CacheInterceptor,
        context: Context
    ): NetworkBuilder {
        return NetworkBuilder(networkConnectionInterceptor, cacheInterceptor, context)
    }

}