package com.example.weatherforecast.di

import android.app.Application
import android.content.Context
import com.example.weatherforecast.data.network.InternetMonitor
import com.example.weatherforecast.data.network.NetworkBuilder
import com.example.weatherforecast.data.network.interceptor.CacheInterceptor
import com.example.weatherforecast.data.network.interceptor.NetworkConnectionInterceptor
import com.example.weatherforecast.domain.repository.IIWeatherRepository
import com.example.weatherforecast.domain.repository.WeatherRepository
import com.example.weatherforecast.ui.WeatherApp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import javax.inject.Named
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
    @Singleton //no need this
    fun provideWeatherRepository(networkClient: NetworkBuilder): IIWeatherRepository {
        return WeatherRepository(networkClient)
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