package com.example.search.detail

import androidx.lifecycle.ViewModel
import com.example.repository.GitHubRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class RepositoryDetailViewModel @Inject constructor(
    private val gitHubRepository: GitHubRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(RepositoryDetailUiState())
    val uiState: StateFlow<RepositoryDetailUiState> = _uiState.asStateFlow()

    fun getRepositoryInMemory(id: Int) {
        val repository = gitHubRepository.getRepositoryInMemory(id)
        _uiState.update { it.copy(repository = repository) }
    }
}
