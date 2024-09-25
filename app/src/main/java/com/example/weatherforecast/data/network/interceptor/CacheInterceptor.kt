package com.example.weatherforecast.data.network.interceptor

import android.content.Context
import com.example.weatherforecast.R
import com.example.weatherforecast.data.network.NoConnectivityException
import com.example.weatherforecast.utils.NetworkUtils
import okhttp3.Interceptor
import okhttp3.Response

/**
 * If the device is offline, the app will attempt to load the cached data (up to a maximum of 4 weeks).
 * If the device is online, the cached response will be used for up to 5 minutes before making a new network request.
 * we can configure the max-age (online cache) and max-stale (offline cache) durations based on our app’s needs.
 */
class CacheInterceptor(private val context: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()

        // If there’s no network connection, forcefully load from cache
        if (!NetworkUtils.isNetworkAvailable(context)) {
            request = request.newBuilder()
                .header(
                    "Cache-Control",
                    "public, only-if-cached, max-stale=604800"
                ) // Cache for 1 weeks((604800 sec)) if offline
                .build()

            val cachedResponse = chain.proceed(request)
            if (cachedResponse.cacheResponse != null) {
                // Return cached data if available
                return cachedResponse
            } else {
                // No cache available, throw an exception
                throw NoConnectivityException(context.getString(R.string.no_internet_connection))
            }

        }
        // Normal network behavior when network is available
        val url = request.url.toString()
        val response = chain.proceed(request)

        // Set Cache-Control headers for online responses
        //customize control for different url
        val cacheControl = response.header("Cache-Control")
        return when {
            url.contains("/weather") -> {
                response.newBuilder()
                    .header(
                        "Cache-Control",
                        "public, max-age=300"
                    ) // Cache for 5 minutes when online
                    .build()
            }

            url.contains("/forecast") -> {
                response.newBuilder()
                    .header(
                        "Cache-Control",
                        "public, max-age=3600"
                    ) //Cache for 1hr when online
                    .build()
            }

            (cacheControl == null || cacheControl.contains("no-store") || cacheControl.contains("no-cache")) -> {
                response.newBuilder()
                    .header(
                        "Cache-Control",
                        "public, max-age=300"
                    ) // Cache for 5 minutes when online
                    .build()
            }

            else -> response
        }
    }
}

