package com.sunilmishra.android.forecastweatherapp.di.module

import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.sunilmishra.android.forecastweatherapp.WeatherForecastApplication
import com.sunilmishra.android.forecastweatherapp.persistance.dao.WeatherForecastDao
import com.sunilmishra.android.forecastweatherapp.persistance.local.AppDatabase
import com.sunilmishra.android.forecastweatherapp.ui.WeatherForecastViewModelFactory
import com.sunilmishra.android.forecastweatherapp.utils.Constants
import com.sunilmishra.android.forecastweatherapp.utils.InternetConnectionUtils
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val app: WeatherForecastApplication) {

    @Provides
    @Singleton
    fun provideApplication(): WeatherForecastApplication = app

    @Provides
    @Singleton
    fun provideDatabase(app: WeatherForecastApplication): AppDatabase = Room.databaseBuilder(
        app,
        AppDatabase::class.java, Constants.DATABASE_NAME
    )
        .fallbackToDestructiveMigration()
        .build()

    @Provides
    @Singleton
    fun provideWeatherForecastDao(
        database: AppDatabase
    ): WeatherForecastDao = database.weatherForecastDao()

    @Provides
    @Singleton
    fun provideWeatherForecastViewModelFactory(
        factory: WeatherForecastViewModelFactory
    ): ViewModelProvider.Factory = factory

    @Provides
    @Singleton
    fun provideInternetConnection(): InternetConnectionUtils = InternetConnectionUtils(app)
}