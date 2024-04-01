package com.example.source.api.adapter

import android.util.Log
import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter

class DateTimeAdapter {
    @FromJson
    fun fromJson(json: String): LocalDateTime {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssz")
        val result = runCatching { LocalDateTime.parse(json, formatter) }
        if (result.isFailure) {
            Log.d(this::class.simpleName, result.exceptionOrNull()!!.message!!)
        }
        return result.getOrNull()!!
    }

    @ToJson
    fun toJson(dateTime: LocalDateTime): String {
        return dateTime.toString()
    }
}
