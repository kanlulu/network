package com.iarc.network.interceptor

import com.iarc.network.params.RequestCommon
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import kotlin.jvm.Throws

class MyHeadInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        if (request.method == "GET") {
            //添加GET 请求公共参数
            val httpUrlBuilder = request.url.newBuilder()
            request = addCommonParameter(httpUrlBuilder, request)
        }

        val builder = request.newBuilder()
        //添加Header公共参数
        addHeaderParameter(builder)
        return chain.proceed(builder.build())
    }

    /**
     * 添加公共Header参数
     */
    private fun addHeaderParameter(builder: Request.Builder) {
        val params = RequestCommon.HEADER_COMMON_PARAMS
        params.map {
            builder.addHeader(it.key, it.value)
        }
    }

    /**
     * 添加GET请求公共参数
     */
    private fun addCommonParameter(
        httpUrlBuilder: HttpUrl.Builder,
        request: Request
    ): Request {
        val params = RequestCommon.REQUEST_COMMON_PARAMS
        params.map {
            httpUrlBuilder.addQueryParameter(it.key, it.value)
        }
        return request.newBuilder().url(httpUrlBuilder.build()).build()
    }
}