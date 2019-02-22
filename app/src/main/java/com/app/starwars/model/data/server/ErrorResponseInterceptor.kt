package com.app.starwars.model.data.server

import okhttp3.Interceptor
import okhttp3.Response

class ErrorResponseInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())
        val code = response.code()
        when (code) {
//            in 400..404 -> throw NotAuthorizedError()
        }
        return response
    }
}