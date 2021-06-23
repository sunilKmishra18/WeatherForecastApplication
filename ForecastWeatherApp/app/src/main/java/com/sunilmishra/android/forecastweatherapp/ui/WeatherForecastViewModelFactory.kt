package com.sunilmishra.android.forecastweatherapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject

class WeatherForecastViewModelFactory @Inject constructor(
    private val weatherForecastViewModel: WeatherForecastViewModel
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WeatherForecastViewModel::class.java)) {
            return weatherForecastViewModel as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}