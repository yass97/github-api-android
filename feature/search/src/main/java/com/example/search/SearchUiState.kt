package com.example.search

import com.example.model.Repository

data class SearchUiState(
    val loading: Boolean = false,
    val keyword: String = "",
    val repositories: List<Repository> = emptyList(),
    val events: List<Event> = emptyList(),
) {
    sealed interface Event {
        data class SearchFailure(val e: Throwable) : Event
    }
}
