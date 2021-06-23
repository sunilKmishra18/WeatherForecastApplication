package com.sunilmishra.android.forecastweatherapp.utils

import android.content.Context
import android.net.ConnectivityManager
import javax.inject.Inject

class InternetConnectionUtils @Inject constructor(private val context: Context) {

    fun isConnectedToInternet(): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetworkInfo
        return activeNetwork != null && activeNetwork.isAvailable && activeNetwork.isConnected
    }
}