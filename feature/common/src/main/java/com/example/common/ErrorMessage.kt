package com.example.common

import android.content.Context
import androidx.annotation.StringRes
import com.example.model.ApiError
import java.net.SocketTimeoutException
import java.net.UnknownHostException

sealed interface ErrorMessage {
    fun getTitle(): String?
    fun getMessage(context: Context): String

    data class ApiErrorMessage(val code: Int, val message: String) : ErrorMessage {
        override fun getTitle(): String = code.toString()
        override fun getMessage(context: Context): String = message
    }

    data class ApplicationErrorMessage(@StringRes val resId: Int) : ErrorMessage {
        override fun getTitle(): String? = null
        override fun getMessage(context: Context): String = context.getString(resId)
    }

    companion object {
        fun from(e: Throwable): ErrorMessage = when (e) {
            is UnknownHostException -> ApplicationErrorMessage(R.string.error_message_network)
            is SocketTimeoutException -> ApplicationErrorMessage(R.string.error_message_timeout)
            is ApiError -> ApiErrorMessage(e.code, e.body ?: "No message")
            else -> ApplicationErrorMessage(R.string.error_message_unknown)
        }
    }
}
