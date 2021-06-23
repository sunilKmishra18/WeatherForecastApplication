package com.sunilmishra.android.forecastweatherapp

import com.sunilmishra.android.forecastweatherapp.utils.StringFormatter
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito
import java.util.*

@RunWith(JUnit4::class)
class StringFormatterTest {
    @Test
    fun convertTimestampToDayAndHourFormatIsNotNullTest() {
        val result=StringFormatter.convertTimestampToDayAndHourFormat(Date().time)
        Assert.assertNotNull(result)
    }

    @Test
    fun convertTimestampToHourFormatIsNotNullTest() {
        val result=StringFormatter.convertTimestampToHourFormat(Date().time,"GMT")
        Assert.assertNotNull(result)
    }

    @Test
    fun convertTimestampToTimeZoneIsNotNullTest() {
        val result=StringFormatter.convertTimestampToTimeZone(Date().time)
        Assert.assertNotNull(result)
    }
}