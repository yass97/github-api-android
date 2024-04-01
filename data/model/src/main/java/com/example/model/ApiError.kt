package com.example.model

data class ApiError(
    val code: Int,
    val body: String?,
) : Throwable()
