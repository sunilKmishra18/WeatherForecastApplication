package com.sunilmishra.android.forecastweatherapp.data.repository

import com.sunilmishra.android.forecastweatherapp.data.model.entities.WeatherForecastEntity
import com.sunilmishra.android.forecastweatherapp.data.remote.RemoteWeatherApiInterface
import com.sunilmishra.android.forecastweatherapp.persistance.dao.WeatherForecastDao
import com.sunilmishra.android.forecastweatherapp.utils.InternetConnectionUtils
import io.reactivex.Observable
import timber.log.Timber
import javax.inject.Inject

class WeatherForecastRepository @Inject constructor(
    private val apiInterface: RemoteWeatherApiInterface,
    private val dao: WeatherForecastDao,
    private val internetConnectionUtils: InternetConnectionUtils
) {

    val TAG = "WeatherForecastRepository"
    fun getWeatherInfo(
        apiKey: String,
        headerType: String,
        cityID: Int
    ): Observable<WeatherForecastEntity> {
        val hasConnection = internetConnectionUtils.isConnectedToInternet()
        var observableFromApi: Observable<WeatherForecastEntity>? = null
        if (hasConnection) {
            observableFromApi =
                getWeatherForecastFromApi(apiKey = apiKey, headerType = headerType, cityID = cityID)
        }
        val observableFromDb = getWeatherForecastFromDb(cityID.toString())

        return if (hasConnection) {
            Observable.concatArrayEager(observableFromApi, observableFromDb)
        } else observableFromDb!!
    }

    private fun getWeatherForecastFromApi(
        apiKey: String,
        headerType: String,
        cityID: Int
    ): Observable<WeatherForecastEntity>? {
        return apiInterface.getLocationDetails(
            apiKey,
            headerType,
            cityID
        )
            .doOnNext {
                Timber.d(TAG + "::getWeatherForecastFromApi()::doOnNext:: List ::" + it)
                dao.saveWeatherForecastInfo(it)
            }
    }

    private fun getWeatherForecastFromDb(cityID: String): Observable<WeatherForecastEntity>? {
        return dao.queryWeatherForecastInfoByCityId(cityID.toInt())
            .toObservable()
            .doOnNext {
                Timber.e(TAG + "::getWeatherForecastFromDb()::doOnNext:: List::" + it)
            }
    }

}