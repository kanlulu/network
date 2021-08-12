package com.iarc.network.requestExt

import androidx.lifecycle.viewModelScope
import com.iarc.base.viewmodel.BaseViewModel
import com.iarc.network.exception.AppException
import com.iarc.network.exception.ExceptionHandle
import com.iarc.network.response.ApiResponse
import com.iarc.network.response.BaseResponse
import kotlinx.coroutines.*

/**
 * author:kanlulu
 * data  :6/4/21 4:17 PM
 **/

fun <T> BaseViewModel.request(
    httpRequest: suspend () -> ApiResponse<T>,
    success: (T) -> Unit,
    error: (AppException) -> Unit
): Job {
    return viewModelScope.launch {
        runCatching {
            httpRequest()
        }.onSuccess {
            runCatching {
                checkResponse(it) { data -> success(data) }
            }.onFailure {
                error(ExceptionHandle.handleException(it))
            }
        }.onFailure {
            error(ExceptionHandle.handleException(it))
        }
    }
}

/**
 * 请求结果过滤，判断请求服务器请求结果是否成功，不成功则会抛出异常
 */
suspend fun <T> checkResponse(
    response: BaseResponse<T>,
    success: suspend CoroutineScope.(T) -> Unit
) {
    coroutineScope {
        when {
            response.isSuccess() -> {
                success(response.getResponseData())
            }
            else -> {
                throw AppException(
                    response.getResponseCode(),
                    response.getResponseMsg(),
                    response.getResponseMsg()
                )
            }
        }
    }
}