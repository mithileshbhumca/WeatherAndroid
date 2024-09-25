package com.example.weatherforecast.data.network.interceptor

import android.content.Context
import com.example.weatherforecast.R
import com.example.weatherforecast.data.network.NoConnectivityException
import com.example.weatherforecast.utils.NetworkUtils
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Response

class NetworkConnectionInterceptor(private val context: Context) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()

        if (!NetworkUtils.isNetworkAvailable(context)) {
            // If network is not available, proceed with cache control
            // Force cache usage in case of no network
            request = request.newBuilder()
                .cacheControl(CacheControl.FORCE_CACHE) // Force cache in case of no network
                .build()
            val cachedResponse = chain.proceed(request)
            if (cachedResponse.cacheResponse != null) {
                // Return cached data if available
                return cachedResponse
            } else {
                // No cache available, throw an exception
                throw NoConnectivityException(context.getString(R.string.no_internet_connection_no_cache))
            }
        }

        return chain.proceed(request)
    }
}
