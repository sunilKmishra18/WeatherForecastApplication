package com.sunilmishra.android.forecastweatherapp.ui

import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.sunilmishra.android.forecastweatherapp.R
import com.sunilmishra.android.forecastweatherapp.data.model.entities.WeatherForecastEntity
import com.sunilmishra.android.forecastweatherapp.utils.Constants
import com.sunilmishra.android.forecastweatherapp.utils.StringFormatter.convertTimestampToDayAndHourFormat
import com.sunilmishra.android.forecastweatherapp.utils.StringFormatter.convertTimestampToHourFormat
import com.sunilmishra.android.forecastweatherapp.utils.StringFormatter.convertTimestampToTimeZone
import com.sunilmishra.android.forecastweatherapp.utils.StringFormatter.convertToValueWithUnit
import com.sunilmishra.android.forecastweatherapp.utils.StringFormatter.currentTimeZone
import com.sunilmishra.android.forecastweatherapp.utils.StringFormatter.unitDegreesCelsius
import com.sunilmishra.android.forecastweatherapp.utils.StringFormatter.unitPercentage
import com.sunilmishra.android.forecastweatherapp.utils.StringFormatter.unitsHPA
import com.sunilmishra.android.forecastweatherapp.utils.StringFormatter.unitsMetresPerSecond
import com.sunilmishra.android.forecastweatherapp.utils.WeatherMathUtils.convertKelvinScaleToCelsius
import com.sunilmishra.android.forecastweatherapp.utils.ext.toast
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_weather_forecast.*
import kotlinx.android.synthetic.main.layout_weather_info.*
import timber.log.Timber
import java.util.*
import javax.inject.Inject

class WeatherForecastActivity : AppCompatActivity() {

    val TAG = "WeatherForecastActivity"

    @Inject
    lateinit var weatherForecastViewModelFactory: WeatherForecastViewModelFactory

    lateinit var weatherForecastViewModel: WeatherForecastViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        makeFullScreen()
        setContentView(R.layout.activity_weather_forecast)
        AndroidInjection.inject(this)

        initializeViewModel()
        initializeView()
        initializeViewModelStatus()
    }

    private fun initializeView() {

        textViewApplicationName.visibility = View.VISIBLE
        progressBar.visibility = View.VISIBLE
        llWeatherInfo.visibility = View.GONE
        loadData()
    }

    private fun initializeViewModel() {
        weatherForecastViewModel = ViewModelProviders.of(this, weatherForecastViewModelFactory).get(
            WeatherForecastViewModel::class.java
        )
    }


    private fun initializeViewModelStatus() {
        weatherForecastViewModel.weatherForecastResult().observe(this,
            Observer<WeatherForecastEntity> {

                if (it != null) {
                    textViewCurrentTime.text = convertTimestampToDayAndHourFormat(Date().time)

                    textViewCityCountry.text =
                        it.name.plus(Constants.COMMA_WITH_SPACE).plus(it.sys!!.country)

                    textViewCurrentTemperature.text =
                        convertToValueWithUnit(
                            2,
                            unitDegreesCelsius,
                            convertKelvinScaleToCelsius(it.main!!.temp)
                        )

                    textViewWeatherSummary.text =
                        it.weather!!.get(0).main.plus(Constants.START_SMALL_BRACKET_WITH_SPACE)
                            .plus(it.weather!!.get(0).description)
                            .plus(Constants.CLOSE_SMALL_BRACKET_WITH_SPACE)

                    textViewSunriseValue.text =
                        convertTimestampToHourFormat(it.sys!!.sunrise!!.toLong(), currentTimeZone)

                    textViewSunsetValue.text =
                        convertTimestampToHourFormat(it.sys!!.sunset!!.toLong(), currentTimeZone)

                    textViewTimezoneValue.text = convertTimestampToTimeZone(it.timezone!!.toLong())

                    textViewCloudPressureValue.text =
                        convertToValueWithUnit(2, unitsHPA, it.main.pressure!!.toDouble())

                    textViewCloudCoverageValue.text =
                        convertToValueWithUnit(2, unitPercentage, it.clouds!!.all!!.toDouble())

                    textViewWindSpeedValue.text =
                        convertToValueWithUnit(2, unitsMetresPerSecond, it.wind!!.speed)

                    textViewHumidityValue.text =
                        convertToValueWithUnit(2, unitPercentage, it.main.humidity!!.toDouble())

                }
            })

        weatherForecastViewModel.weatherForecastError().observe(this, Observer<String> {
            if (it != null) {
                toast(resources.getString(R.string.error_with_fetching_weather_details_message) + it)
                Timber.e(
                    TAG + "::initializeViewModelStatus::weatherForecastError()::Error::" + resources.getString(
                        R.string.error_with_fetching_weather_details_message
                    ) + it
                )

            }
        })

        weatherForecastViewModel.weatherForecastLoader().observe(this, Observer<Boolean> {
            if (it == false) {
                textViewApplicationName.visibility = View.GONE
                progressBar.visibility = View.GONE
                llWeatherInfo.visibility = View.VISIBLE
            }
        })
    }

    private fun makeFullScreen() {
        // Remove Title
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        // Make Fullscreen
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        // Hide the toolbar
        supportActionBar?.hide()
    }

    private fun loadData() {
        weatherForecastViewModel.loadWeatherForecast(Constants.CITY_ID)
    }

    override fun onDestroy() {
        weatherForecastViewModel.disposeElements()
        super.onDestroy()
    }

}