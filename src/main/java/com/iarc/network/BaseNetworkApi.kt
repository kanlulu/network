package com.iarc.network

import me.jessyan.retrofiturlmanager.RetrofitUrlManager
import okhttp3.OkHttpClient
import retrofit2.Retrofit

/**
 * author:kanlulu
 * data  :6/4/21 3:05 PM
 **/
abstract class BaseNetworkApi {

    fun <T> getApi(serviceClass: Class<T>, baseUrl: String): T {
        val retrofitBuilder = Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
        return setRetrofitBuilder(retrofitBuilder).build().create(serviceClass)
    }

    /**
     * 配置http
     */
    private val okHttpClient: OkHttpClient
        get() {
            var builder = RetrofitUrlManager.getInstance().with(OkHttpClient.Builder())
            builder = setHttpClientBuilder(builder)
            //绕过代理、防止抓包  外网代理连国内也不行
//            builder.proxy(Proxy.NO_PROXY)
            return builder.build()
        }

    /**
     * 实现重写父类的setRetrofitBuilder方法，
     * 在这里可以对Retrofit.Builder做任意操作，比如添加GSON解析器，Protocol
     */
    abstract fun setRetrofitBuilder(builder: Retrofit.Builder): Retrofit.Builder

    /**
     * 实现重写父类的setHttpClientBuilder方法，
     * 在这里可以添加拦截器，可以对 OkHttpClient.Builder 做任意操作
     */
    abstract fun setHttpClientBuilder(builder: OkHttpClient.Builder): OkHttpClient.Builder
}