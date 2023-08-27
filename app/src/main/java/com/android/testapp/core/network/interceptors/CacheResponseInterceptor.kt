package com.android.testapp.core.network.interceptors

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response

class CachePostResponseInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
// Before the actual request
        if (request.isCachePostRequest()) {
            request = request.newBuilder()
                .method("POST", request.body)
                .build()
        }
// Initiate request
        var response = chain.proceed(request)
// Get the result of the request
// Cache for this interface
        if (response.request.isCachePostRequest()) {
            val builder = response.request.newBuilder()
// hold POST Change to GET
                .method("GET", null)
// preservation body
            saveRequestBody(builder, request.body)
            response = response.newBuilder()
                .request(builder.build())
                .removeHeader("Pragma")
// cache 60 second
                .addHeader("Cache-Control", "max-age=1800")
                .build()
        }
        return response
    }

    private fun saveRequestBody(builder: Request.Builder, body: RequestBody?) {
        val bodyField = builder.javaClass.getDeclaredField("body")
        bodyField.isAccessible = true
        bodyField.set(builder, body)
    }

    private fun Request.isCachePostRequest(): Boolean = run {
        url.toString().contains("APP_INFO_URL", true)
    }
}