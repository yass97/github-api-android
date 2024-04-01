package com.example.source.api

import com.example.model.ApiError
import retrofit2.Response

suspend fun <T> handleApi(execute: suspend () -> Response<T>): Result<T> {
    val result = runCatching { execute() }
    return if (result.isSuccess) {
        val response = result.getOrNull()!!
        if (response.isSuccessful) {
            Result.success(response.body()!!)
        } else {
            val error = ApiError(response.code(), response.errorBody()?.string())
            Result.failure(error)
        }
    } else {
        Result.failure(result.exceptionOrNull()!!)
    }
}
