package hm.assignment.app.util

import hm.assignment.app.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

/**
 * Created on 2022-03-05.
 * CopyrightⒸ Kagge
 */
class HMInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val originalUrl = originalRequest.url

        val newUrl = originalUrl.newBuilder().addQueryParameter("access_key", BuildConfig.COUNTRY_LAYER_API_KEY).build()
        val newRequest = originalRequest.newBuilder().url(newUrl)
        "DEBUG: Making API-call to $newUrl".log()

        val response = chain.proceed(newRequest.build())
        "API-ResponseBody: ${response.peekBody(2048).string()}".log()
        return response
    }
}