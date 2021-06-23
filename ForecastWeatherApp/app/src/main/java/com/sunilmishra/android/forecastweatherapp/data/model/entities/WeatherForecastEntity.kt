package com.sunilmishra.android.forecastweatherapp.data.model.entities

import android.annotation.SuppressLint
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import android.os.Parcelable
import androidx.room.*
import com.sunilmishra.android.forecastweatherapp.data.model.converters.ListTypeConverter
import com.sunilmishra.android.forecastweatherapp.utils.Constants

@SuppressLint("ParcelCreator")
@Parcelize
@Entity(tableName = Constants.DATABASE_TABLE_NAME)
data class WeatherForecastEntity(
    @SerializedName("base")
    val base: String?,
    @SerializedName("clouds") @Embedded
    val clouds: Clouds?,
    @SerializedName("cod")
    val cod: Int? = 0,
    @SerializedName("coord") @Embedded
    val coord: Coord?,
    @SerializedName("dt")
    val dt: Int?,
    @SerializedName("id") @PrimaryKey @ColumnInfo(name = "city_id")
    val id: Int? = 0,
    @SerializedName("main") @Embedded
    val main: Main?,
    @SerializedName("name") @ColumnInfo(name = "city_name")
    val name: String?,
    @SerializedName("sys") @Embedded
    val sys: Sys?,
    @SerializedName("timezone")
    val timezone: Int?,
    @SerializedName("visibility")
    val visibility: Int?,
    @SerializedName("weather") @TypeConverters(ListTypeConverter::class)
    val weather: List<Weather>? = ArrayList(),
    @SerializedName("wind") @Embedded
    val wind: Wind?
) : Parcelable {
    @SuppressLint("ParcelCreator")
    @Parcelize
    @Entity
    data class Clouds(
        @SerializedName("all")
        val all: Int? = 0
    ) : Parcelable

    @SuppressLint("ParcelCreator")
    @Parcelize
    @Entity
    data class Coord(
        @SerializedName("lat")
        val lat: Double? = 0.0,
        @SerializedName("lon")
        val lon: Double? = 0.0
    ) : Parcelable

    @SuppressLint("ParcelCreator")
    @Parcelize
    @Entity
    data class Main(
        @SerializedName("humidity")
        val humidity: Int?,
        @SerializedName("pressure")
        val pressure: Int?,
        @SerializedName("temp")
        val temp: Double?,
        @SerializedName("temp_max")
        val tempMax: Double?,
        @SerializedName("temp_min")
        val tempMin: Double?
    ) : Parcelable

    @SuppressLint("ParcelCreator")
    @Parcelize
    @Entity
    data class Sys(
        @SerializedName("country")
        val country: String?,
        @SerializedName("id") @ColumnInfo(name = "sys_id")
        val id: Int?,
        @SerializedName("sunrise")
        val sunrise: Int?,
        @SerializedName("sunset")
        val sunset: Int?,
        @SerializedName("type")
        val type: Int?
    ) : Parcelable

    @SuppressLint("ParcelCreator")
    @Parcelize
    @Entity
    data class Weather(
        @SerializedName("description")
        val description: String?,
        @SerializedName("icon")
        val icon: String?,
        @PrimaryKey @SerializedName("id") @ColumnInfo(name = "weather_id")
        val id: Int?,
        @SerializedName("main")
        val main: String?
    ) : Parcelable

    @SuppressLint("ParcelCreator")
    @Parcelize
    @Entity
    data class Wind(
        @SerializedName("deg")
        val deg: Int?,
        @SerializedName("speed")
        val speed: Double?
    ) : Parcelable
}