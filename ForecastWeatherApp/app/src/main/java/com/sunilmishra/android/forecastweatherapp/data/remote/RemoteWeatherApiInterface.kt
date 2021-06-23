package com.sunilmishra.android.forecastweatherapp.data.remote


import com.sunilmishra.android.forecastweatherapp.data.model.entities.WeatherForecastEntity
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface RemoteWeatherApiInterface {

    @GET("weather")
    fun getLocationDetails(
        @Query("appid") key: String, @Header("Accept") type: String,
        @Query("id") id: Int
    ): Observable<WeatherForecastEntity>
}