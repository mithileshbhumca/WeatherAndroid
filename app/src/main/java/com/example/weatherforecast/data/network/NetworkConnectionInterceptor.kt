package com.example.weatherforecast.data.network

import android.content.Context
import com.example.weatherforecast.R
import com.example.weatherforecast.utils.NetworkUtils
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class NetworkConnectionInterceptor(private val context: Context) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        if (!NetworkUtils.isNetworkAvailable(context)) {
            throw NoConnectivityException(context.getString(R.string.no_internet_connection))
        }

        return chain.proceed(chain.request())
    }

    class NoConnectivityException(message: String) : IOException(message)
}
