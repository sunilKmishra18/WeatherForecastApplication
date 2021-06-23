package com.sunilmishra.android.forecastweatherapp.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewAssertion
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.sunilmishra.android.forecastweatherapp.R
import kotlinx.android.synthetic.main.activity_weather_forecast.*
import kotlinx.android.synthetic.main.layout_weather_info.*
import org.junit.Assert
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.anyString
import org.mockito.Mockito.mock

@RunWith(AndroidJUnit4::class)
class WeatherForecastActivityTest{

    @get:Rule
    var activityRule: ActivityScenarioRule<WeatherForecastActivity>
            = ActivityScenarioRule(WeatherForecastActivity::class.java)

    lateinit var weatherForecastViewModel: WeatherForecastViewModel

    @Before
    fun setup() {
        activityRule.scenario.onActivity { activity ->
            weatherForecastViewModel = activity.weatherForecastViewModel
        }
    }

    @Test
    fun testInitializeViewDuringLaunch() {
        onView(withId(R.id.textViewApplicationName))
            .check(matches(withText(R.string.app_name)))

        onView(withId(R.id.textViewApplicationName))
            .isVisible()

        onView(withId(R.id.progressBar))
            .isVisible()

        onView(withId(R.id.llWeatherInfo))
            .isGone()

    }

    private fun ViewInteraction.isGone() = getViewAssertion(ViewMatchers.Visibility.GONE)

    private fun ViewInteraction.isVisible() = getViewAssertion(ViewMatchers.Visibility.VISIBLE)

    private fun ViewInteraction.isInvisible() = getViewAssertion(ViewMatchers.Visibility.INVISIBLE)

    private fun getViewAssertion(visibility: ViewMatchers.Visibility): ViewAssertion? {
        return ViewAssertions.matches(ViewMatchers.withEffectiveVisibility(visibility))
    }
}