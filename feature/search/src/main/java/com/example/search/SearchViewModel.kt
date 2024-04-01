package com.example.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.repository.GitHubRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val repository: GitHubRepository) : ViewModel() {
    private val _searchUiState = MutableStateFlow(SearchUiState())
    val searchUiState: StateFlow<SearchUiState> = _searchUiState.asStateFlow()

    fun onChange(keyword: String) {
        _searchUiState.update { it.copy(keyword = keyword) }
    }

    fun search() {
        _searchUiState.update { it.copy(loading = true) }
        viewModelScope.launch(Dispatchers.IO) {
            repository.search(searchUiState.value.keyword)
                .onSuccess { repositories ->
                    _searchUiState.update {
                        it.copy(
                            loading = false,
                            repositories = repositories,
                        )
                    }
                }
                .onFailure { e ->
                    _searchUiState.update {
                        it.copy(
                            loading = false,
                            events = it.events + SearchUiState.Event.SearchFailure(e),
                        )
                    }
                }
        }
    }

    fun consumeEvent(event: SearchUiState.Event) {
        val newEvents = _searchUiState.value.events.filterNot { it == event }
        _searchUiState.update { it.copy(events = newEvents) }
    }
}
