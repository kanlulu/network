package com.iarc.network.requestExt

import androidx.lifecycle.viewModelScope
import com.iarc.base.viewmodel.BaseViewModel
import com.iarc.base.viewmodel.BaseViewModel.Companion.PAGE_START_INDEX
import com.iarc.base.viewmodel.PagingResult
import com.iarc.network.exception.AppException
import com.iarc.network.exception.ExceptionHandle
import com.iarc.network.response.ApiResponse
import com.iarc.network.utils.ReflectUtils
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

/**
 * author:kanlulu
 * data  :6/5/21 9:44 AM
 **/

/**
 * 限定数据结构
 * 1. data直接是集合时可用  data:[]
 * 2. data包含一个名为list的集合
 *  data:{
 *      list:[]
 *  }
 */
fun <T> BaseViewModel.pageRequest(
    httpRequest: suspend () -> ApiResponse<T>,
    //pageResult包含是否请求来数据、是否有下一页(loadEnd)
    success: (T, pageResult: PagingResult) -> Unit,
    error: (AppException) -> Unit
): Job {
    return viewModelScope.launch {
        runCatching {
            httpRequest()
        }.onSuccess {
            runCatching {
                checkResponse(it) { data ->
                    run {
                        if (data == null) {
                            throw Throwable("data is null")
                        }
                        //分页加载结果
                        //支持中数据格式 data直接是集合 或 data是对象包含一个名称是list的集合
                        val pageResult: PagingResult = if (data is List<*>) {
                            PagingResult(
                                isSuccess = true,
                                hasNext = (data as MutableList<*>).size >= pageSize,
                                isFirstPage = pageNo == PAGE_START_INDEX
                            )
                        } else {
                            val pageDataSize = ReflectUtils.pageData(data)
                            PagingResult(
                                isSuccess = pageDataSize != -1,
                                hasNext = pageDataSize >= pageSize,
                                isFirstPage = pageNo == PAGE_START_INDEX
                            )
                        }

                        success(data, pageResult)
                        if (pageResult.isSuccess) pageNo++
                    }
                }
            }.onFailure {
                error(ExceptionHandle.handleException(it))
            }
        }.onFailure {
            error(ExceptionHandle.handleException(it))
        }
    }
}

