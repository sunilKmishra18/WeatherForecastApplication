package com.sunilmishra.android.forecastweatherapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.sunilmishra.android.forecastweatherapp.data.model.entities.WeatherForecastEntity
import com.sunilmishra.android.forecastweatherapp.data.remote.RemoteWeatherApiInterface
import com.sunilmishra.android.forecastweatherapp.utils.Constants
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.*
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.nio.charset.Charset

@RunWith(JUnit4::class)
class RemoteWeatherApiInterfaceTest {

    @Rule
    @JvmField
    var instantExecutor = InstantTaskExecutorRule()

    private lateinit var webService: RemoteWeatherApiInterface
    private lateinit var mockWebServer: MockWebServer

    @Before
    fun setup() {
        mockWebServer = MockWebServer()
        mockWebServer.start()

        webService = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClient())
            .build()
            .create(RemoteWeatherApiInterface::class.java)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun getLocationDetailsTest() {
        val content =
            this.javaClass.classLoader!!.getResource("get-locationdetails-cases/valid.json").readText(
                Charset.forName("UTF-8")
            )
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(content)
        )

        val weatherForecastEntity = WeatherForecastEntity(
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

        val response = webService.getLocationDetails(
            BuildConfig.API_KEY,
            Constants.HEADER_TYPE,
            Constants.CITY_ID
        ).blockingSingle()

        Assert.assertEquals(response.name, weatherForecastEntity.name)
    }


}