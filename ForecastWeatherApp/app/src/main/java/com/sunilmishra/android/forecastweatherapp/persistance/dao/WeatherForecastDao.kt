package com.sunilmishra.android.forecastweatherapp.persistance.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sunilmishra.android.forecastweatherapp.data.model.entities.WeatherForecastEntity
import io.reactivex.Single

@Dao
interface WeatherForecastDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveWeatherForecastInfo(weatherForecastEntity: WeatherForecastEntity)

    @Query("SELECT * FROM weatherforecast WHERE city_id LIKE :id")
    fun queryWeatherForecastInfoByCityId(id: Int): Single<WeatherForecastEntity>
}