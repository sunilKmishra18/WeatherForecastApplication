package com.sunilmishra.android.forecastweatherapp.data.model.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.sunilmishra.android.forecastweatherapp.data.model.entities.WeatherForecastEntity.Weather

class ListTypeConverter {
    var gson = Gson()

    @TypeConverter
    fun fromStringToWeatherObjectList(data: String?): List<Weather>? {
        val objects = gson.fromJson(data, Array<Weather>::class.java) as Array<Weather>
        val list = objects.toList()
        return list
    }

    @TypeConverter
    fun weatherObjectListToString(weatherObjects: List<Weather>?): String? {
        return gson.toJson(weatherObjects)
    }

}