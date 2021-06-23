package com.sunilmishra.android.forecastweatherapp.di.module

import com.sunilmishra.android.forecastweatherapp.data.remote.RemoteWeatherApiInterface
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import timber.log.Timber
import javax.inject.Singleton

@Module
class NetworkModule(private val baseUrl: String) {
    @Provides
    @Singleton
    fun providesOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient = OkHttpClient.Builder().addInterceptor(
        httpLoggingInterceptor
    ).build()

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor(
            HttpLoggingInterceptor.Logger { message -> Timber.tag("NETWORK: ").i(message) })
        interceptor.level = HttpLoggingInterceptor.Level.BASIC
        return interceptor
    }

    @Provides
    @Singleton
    fun providesRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun providesWeatherForecastApiInterface(retrofit: Retrofit): RemoteWeatherApiInterface =
        retrofit.create(
            RemoteWeatherApiInterface::class.java
        )

}


