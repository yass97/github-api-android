package com.example.source.api.response

import com.example.model.Repository
import com.squareup.moshi.Json

data class RepositorySearchResponse(
    val items: List<Repository>,
    @Json(name = "total_count") val totalCount: Int,
    @Json(name = "incomplete_results") val incompleteResults: Boolean,
)
