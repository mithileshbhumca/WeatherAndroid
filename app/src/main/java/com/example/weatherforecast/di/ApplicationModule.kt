package com.example.weatherforecast.di

import android.content.Context
import com.example.weatherforecast.ui.WeatherApp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {

    @Provides
    @Singleton
    fun provideApplicationContext(context: WeatherApp): Context {
        return context.applicationContext
    }

//
////    @Provides
//    @Singleton
//    fun provideWeatherRepository(networkClient: RetrofitModule?): IIWeatherRepository {
//        return WeatherRepository(networkClient)
//    }
}