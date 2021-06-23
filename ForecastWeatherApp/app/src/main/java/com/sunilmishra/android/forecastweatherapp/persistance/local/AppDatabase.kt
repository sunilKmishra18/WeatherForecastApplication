package com.sunilmishra.android.forecastweatherapp.persistance.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.sunilmishra.android.forecastweatherapp.data.model.converters.ListTypeConverter
import com.sunilmishra.android.forecastweatherapp.data.model.entities.*
import com.sunilmishra.android.forecastweatherapp.persistance.dao.WeatherForecastDao
import com.sunilmishra.android.forecastweatherapp.utils.Constants


@Database(
    entities = arrayOf(WeatherForecastEntity::class, WeatherForecastEntity.Weather::class),
    version = Constants.DB_SCHEMA_VERSION
)
@TypeConverters(ListTypeConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun weatherForecastDao(): WeatherForecastDao
}