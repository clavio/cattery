package com.ciphra.android.thecattery

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

const val NO_INTERNET = 0
const val REQUEST_ERROR = 1

fun isConnected(context : Context) : Boolean{
    val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
    return activeNetwork?.isConnectedOrConnecting == true
}