package com.sunilmishra.android.forecastweatherapp.utils


object WeatherMathUtils {

    fun convertKelvinScaleToCelsius(temperatureKelvinScale: Double?): Double? =
        if (temperatureKelvinScale != null)
            (temperatureKelvinScale - 273.15) else null
}