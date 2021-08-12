package com.iarc.network.response

data class ApiResponse<T>(val code: Int, val data: T, val message: String, val success: Boolean) :
    BaseResponse<T>() {

    override fun isSuccess() = success

    override fun getResponseCode() = code

    override fun getResponseData() = data

    override fun getResponseMsg() = message

}