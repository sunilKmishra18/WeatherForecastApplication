package com.sunilmishra.android.forecastweatherapp.utils.ext

import android.content.Context
import android.net.ConnectivityManager
import android.util.DisplayMetrics
import android.widget.Toast

@Suppress("DEPRECATION")
fun Context.isNetworkConnected(): Boolean {
    val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    return cm.activeNetworkInfo?.isConnectedOrConnecting ?: false
}

fun Context.convertDpToPixel(dp: Float): Float {
    return dp * (this.resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
}

fun Context.toast(msg: String) = Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
