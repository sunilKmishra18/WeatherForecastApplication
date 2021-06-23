package com.sunilmishra.android.forecastweatherapp.di.component

import com.sunilmishra.android.forecastweatherapp.WeatherForecastApplication
import com.sunilmishra.android.forecastweatherapp.di.module.AppModule
import com.sunilmishra.android.forecastweatherapp.di.module.BuildersModule
import com.sunilmishra.android.forecastweatherapp.di.module.NetworkModule
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [(AndroidInjectionModule::class), (BuildersModule::class), (AppModule::class), (NetworkModule::class)]
)
interface AppComponent {
    fun inject(app: WeatherForecastApplication)
}