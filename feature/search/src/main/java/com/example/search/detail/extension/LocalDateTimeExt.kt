package com.example.search.detail.extension

import org.threeten.bp.LocalDateTime

fun LocalDateTime.formatDateTime(): String {
    return "${toLocalDate()} ${toLocalTime()}"
}
