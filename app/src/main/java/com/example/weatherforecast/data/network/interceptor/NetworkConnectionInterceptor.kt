package com.example.weatherforecast.data.network.interceptor

import com.example.weatherforecast.data.network.NoConnectivityException
import com.example.weatherforecast.data.network.InternetMonitor
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class NetworkConnectionInterceptor @Inject constructor(
    private val internetMonitor: InternetMonitor,
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()

        if (!internetMonitor.isNetworkAvailable()) {
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
                throw NoConnectivityException("No network available and no cache data found.")
            }
        }

        return chain.proceed(request)
    }
}
