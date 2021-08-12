package com.iarc.network.utils

import android.text.TextUtils
import android.util.Log
import com.iarc.base.BuildConfig

/**
 * author:kanlulu
 * data  :6/4/21 3:28 PM
 **/
object NetworkLogUtils {
    private const val DEFAULT_TAG = "iarc-tag"
    fun debugInfo(tag: String?, msg: String?) {
        if (!BuildConfig.DEBUG) {
            return
        }
        Log.d(tag, msg?:"")
    }

    fun debugInfo(msg: String?) {
        debugInfo(
            DEFAULT_TAG,
            msg
        )
    }

    fun warnInfo(tag: String?, msg: String?) {
        if (!BuildConfig.DEBUG || TextUtils.isEmpty(msg)) {
            return
        }
        Log.w(tag, msg?:"")
    }

    fun warnInfo(msg: String?) {
        warnInfo(
            DEFAULT_TAG,
            msg
        )
    }

    /**
     * 这里使用自己分节的方式来输出足够长度的 message
     *
     * @param tag 标签
     * @param msg 日志内容
     */
    fun debugLongInfo(tag: String?, msg: String) {
        var msg = msg
        if (!BuildConfig.DEBUG || TextUtils.isEmpty(msg)) {
            return
        }
        msg = msg.trim { it <= ' ' }
        var index = 0
        val maxLength = 3500
        var sub: String
        while (index < msg.length) {
            sub = if (msg.length <= index + maxLength) {
                msg.substring(index)
            } else {
                msg.substring(index, index + maxLength)
            }
            index += maxLength
            Log.d(tag, sub.trim { it <= ' ' })
        }
    }

    fun debugLongInfo(msg: String) {
        debugLongInfo(
            DEFAULT_TAG,
            msg
        )
    }
}