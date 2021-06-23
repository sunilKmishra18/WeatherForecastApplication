package com.sunilmishra.android.forecastweatherapp.di.module

import com.sunilmishra.android.forecastweatherapp.ui.WeatherForecastActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class BuildersModule {
    @ContributesAndroidInjector
    abstract fun contributeWeatherForecastActivity(): WeatherForecastActivity
}