package com.sunilmishra.android.forecastweatherapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sunilmishra.android.forecastweatherapp.BuildConfig
import com.sunilmishra.android.forecastweatherapp.data.model.entities.WeatherForecastEntity
import com.sunilmishra.android.forecastweatherapp.data.repository.WeatherForecastRepository
import com.sunilmishra.android.forecastweatherapp.utils.Constants
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class WeatherForecastViewModel @Inject constructor(
    private val weatherForecastRepository: WeatherForecastRepository
) : ViewModel() {

    val TAG = "WeatherForecastViewModel"

    var weatherForecastResult: MutableLiveData<WeatherForecastEntity> = MutableLiveData()
    var weatherForecastError: MutableLiveData<String> = MutableLiveData()
    var weatherForecastLoader: MutableLiveData<Boolean> = MutableLiveData()

    private lateinit var disposableObserver: DisposableObserver<WeatherForecastEntity>


    fun weatherForecastResult(): LiveData<WeatherForecastEntity> {
        return weatherForecastResult
    }

    fun weatherForecastError(): LiveData<String> {
        return weatherForecastError
    }

    fun weatherForecastLoader(): LiveData<Boolean> {
        return weatherForecastLoader
    }

    fun loadWeatherForecast(cityId: Int) {
        disposableObserver = object : DisposableObserver<WeatherForecastEntity>() {
            override fun onComplete() {

            }

            override fun onNext(weatherForecast: WeatherForecastEntity) {
                weatherForecastResult.postValue(weatherForecast)
                weatherForecastLoader.postValue(false)
            }

            override fun onError(e: Throwable) {
                weatherForecastError.postValue(e.message)
                weatherForecastLoader.postValue(false)
            }
        }

        weatherForecastRepository.getWeatherInfo(BuildConfig.API_KEY, Constants.HEADER_TYPE, cityId)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .debounce(2, TimeUnit.HOURS)
            .subscribe(disposableObserver)
    }

    fun disposeElements() {
        if (!disposableObserver.isDisposed) disposableObserver.dispose()
    }
}