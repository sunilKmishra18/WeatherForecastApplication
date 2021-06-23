package com.sunilmishra.android.forecastweatherapp

import android.app.Application
import com.sunilmishra.android.forecastweatherapp.di.component.DaggerAppComponent
import com.sunilmishra.android.forecastweatherapp.di.module.AppModule
import com.sunilmishra.android.forecastweatherapp.di.module.NetworkModule
import com.sunilmishra.android.forecastweatherapp.utils.Constants
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import timber.log.Timber
import javax.inject.Inject

class WeatherForecastApplication : Application(), HasAndroidInjector {

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    override fun onCreate() {
        super.onCreate()
        initDaggar()
        initTimber()
    }

    private fun initDaggar() {
        @Suppress("DEPRECATION")
        DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .networkModule(NetworkModule(Constants.BASE_URL))
            .build()
            .inject(this)
    }

    private fun initTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    override fun androidInjector(): AndroidInjector<Any> = androidInjector
}