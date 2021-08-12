package com.iarc.network

import com.google.gson.GsonBuilder
import com.iarc.network.interceptor.MyHeadInterceptor
import com.iarc.network.interceptor.logging.LogInterceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * author:kanlulu
 * data  :6/4/21 3:18 PM
 **/
class NetworkApi : BaseNetworkApi() {

    companion object {
        val INSTANCE: NetworkApi by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            NetworkApi()
        }

        private const val REQUEST_TYPE = "application/json;charset=UTF-8"

        fun createBody(obj: Any): RequestBody {
            return obj.toString().toRequestBody(REQUEST_TYPE.toMediaType())
        }
    }

    override fun setRetrofitBuilder(builder: Retrofit.Builder): Retrofit.Builder {
        return builder.apply {
            addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
        }
    }

    override fun setHttpClientBuilder(builder: OkHttpClient.Builder): OkHttpClient.Builder {
        builder.apply {
            addInterceptor(MyHeadInterceptor())
            //两个logging拦截器都加上 一个方便阅读一个方便复制
//            addInterceptor(LogInterceptor())
            connectTimeout(10, TimeUnit.SECONDS)
        }
        return builder
    }
}