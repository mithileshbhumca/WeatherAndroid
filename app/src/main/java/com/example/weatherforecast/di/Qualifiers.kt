package com.example.weatherforecast.di

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class MainApi

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AuthApi