package com.sunilmishra.android.forecastweatherapp.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.sunilmishra.android.forecastweatherapp.BuildConfig
import com.sunilmishra.android.forecastweatherapp.data.model.entities.WeatherForecastEntity
import com.sunilmishra.android.forecastweatherapp.data.repository.WeatherForecastRepository
import com.sunilmishra.android.forecastweatherapp.utils.Constants

import org.junit.Assert.*

import org.mockito.Mock
import org.mockito.MockitoAnnotations
import io.reactivex.Observable
import org.junit.*
import java.util.*


class WeatherForecastViewModelTest {

    @Mock
    lateinit var weatherForecastRepository: WeatherForecastRepository

    @Mock
    lateinit var mockLiveDataObserverError: Observer<String>

    @Rule
    @JvmField
    var testSchedulerRule = RxImmediateSchedulerRule()

    @Rule
    @JvmField
    val ruleForLivaData = InstantTaskExecutorRule()

    lateinit var viewModel: WeatherForecastViewModel

    lateinit var weatherForecastEntity: WeatherForecastEntity

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        viewModel = WeatherForecastViewModel(weatherForecastRepository)
        weatherForecastEntity = WeatherForecastEntity(
            base = "stations",
            clouds = WeatherForecastEntity.Clouds(
                all = 54
            ),
            cod = 200,
            coord = WeatherForecastEntity.Coord(lat = 12.98, lon = 77.6),
            dt = 1572935089,
            id = 1277333,
            main = WeatherForecastEntity.Main(
                humidity = 51,
                pressure = 1016,
                temp = 300.93,
                tempMax = 302.59,
                tempMin = 300.15
            ),
            name = "Bangalore",
            sys = WeatherForecastEntity.Sys(
                country = "IN",
                id = 9205,
                sunrise = 1572914658,
                sunset = 1572956521,
                type = 1
            ),
            timezone = 19800,
            visibility = 10000,
            wind = WeatherForecastEntity.Wind(deg = 50, speed = 3.1)
        )
    }

    @After
    fun tearDown() {
    }

    @Test
    fun loadWeatherForecastSuccess() {
        //Setting how up the mock behaves
        whenever(weatherForecastRepository.getWeatherInfo(BuildConfig.API_KEY, Constants.HEADER_TYPE, Constants.CITY_ID)).thenReturn(
            Observable.just(weatherForecastEntity))

        //Fire the real method
        viewModel.loadWeatherForecast(Constants.CITY_ID)

        //Check that our live data is updated
        Assert.assertEquals(weatherForecastEntity, viewModel.weatherForecastResult.value)
    }

    @Test
    fun loadWeatherForecastError() {

        //Setting how up the mock behaves
        whenever(weatherForecastRepository.getWeatherInfo(BuildConfig.API_KEY, Constants.HEADER_TYPE, Constants.CITY_ID)).thenReturn(
            Observable.error(Throwable()))


        viewModel.weatherForecastError.observeForever(mockLiveDataObserverError)

        //Fire the real method
        viewModel.loadWeatherForecast(Constants.CITY_ID)

        verify(mockLiveDataObserverError, times(0)).onChanged(any())
    }
}