package com.example.source.api.interceptor

import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor(private val apiToken: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
            .newBuilder()
            .header("Accept", "application/vnd.github+json")
            .header("X-GitHub-Api-Version", "2022-11-28")
            .header(AUTHORIZATION_HEADER, "Bearer $apiToken")
            .build()
        return chain.proceed(request)
    }

    companion object {
        const val AUTHORIZATION_HEADER = "Authorization"
    }
}
