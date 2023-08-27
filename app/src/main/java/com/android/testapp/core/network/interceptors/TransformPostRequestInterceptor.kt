package com.android.testapp.core.network.interceptors

import okhttp3.*
import java.util.concurrent.TimeUnit

class TransformPostRequestInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        if (request.isCachePostRequest()) {
            val builder = request.newBuilder()
                .method("GET", null)
                .cacheControl(
                    CacheControl.Builder()
                        .maxAge(1800, TimeUnit.SECONDS)
                        .build()
                )
            saveRequestBody(builder, request.body)
            request = builder.build()
        }
        return chain.proceed(request)
    }

    private fun saveRequestBody(builder: Request.Builder, body: RequestBody?) {
        val bodyField = builder.javaClass.getDeclaredField("body")
        bodyField.isAccessible = true
        bodyField.set(builder, body)
    }

    private fun Request.isCachePostRequest(): Boolean = run {
        url.toString().contains(this.url.toString(), true)
    }
}