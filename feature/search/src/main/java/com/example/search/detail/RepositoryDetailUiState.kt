package com.example.search.detail

import com.example.model.Repository

data class RepositoryDetailUiState(
    val loading: Boolean = false,
    val repository: Repository? = null,
)
