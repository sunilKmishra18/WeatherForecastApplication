package com.sunilmishra.android.forecastweatherapp.utils

import java.text.SimpleDateFormat
import java.util.*

object StringFormatter {
    val unitPercentage = "%"
    val unitDegrees = "\u00b0"
    val unitsMetresPerSecond = " m/s"
    val unitDegreesCelsius = "\u2103"
    val unitsHPA = " hPA"
    val currentTimeZone = "GMT+05:30"

    fun convertTimestampToDayAndHourFormat(timestamp: Long): String {
        val DAY_HOUR_MINUTE = "dd MMM yyyy EEEE, HH:mm"
        val formatter = SimpleDateFormat(DAY_HOUR_MINUTE, Locale.ENGLISH)

        val dateFormat = formatter.format(Date(timestamp))
        return dateFormat
    }

    fun convertTimestampToHourFormat(timestamp: Long, timeZone: String?): String {
        val HOUR_MINUTE = "HH:mm"
        val formatter = SimpleDateFormat(HOUR_MINUTE)
        formatter.setTimeZone(TimeZone.getTimeZone(timeZone))

        val dayName = formatter.format(Date(timestamp * 1000))
        return dayName
    }

    fun convertTimestampToTimeZone(timestamp: Long): String {
        val formatter = SimpleDateFormat("zzzz", Locale.ENGLISH)
        val format = formatter.format(Date(timestamp))
        return format
    }

    fun convertToValueWithUnit(precision: Int, unitSymbol: String, value: Double?): String {
        return getPrecision(precision).format(value) + unitSymbol
    }

    private fun getPrecision(precision: Int): String {
        return "%." + precision + "f"
    }
}

