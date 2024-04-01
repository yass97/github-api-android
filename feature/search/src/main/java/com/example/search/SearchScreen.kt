package com.example.search

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.common.ErrorDialog
import com.example.common.ErrorMessage
import com.example.search.component.RepositoryList
import com.example.search.component.SearchBarSection

@Composable
fun SearchScreen(
    viewModel: SearchViewModel = hiltViewModel(),
    onItemClick: (Int) -> Unit,
) {
    val uiState by viewModel.searchUiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "Repository Search") })
        },
    ) { paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            SearchBarSection(
                keyword = uiState.keyword,
                isSearchEnabled = !uiState.loading && uiState.keyword.isNotBlank(),
                onChange = viewModel::onChange,
                onSearchClick = viewModel::search,
            )
            if (uiState.loading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.TopCenter,
                ) {
                    CircularProgressIndicator()
                }
            } else {
                if (uiState.repositories.isEmpty()) {
                    Text(text = "No repository")
                } else {
                    RepositoryList(
                        repositories = uiState.repositories,
                        onClick = onItemClick,
                    )
                }
            }
        }

        uiState.events.firstOrNull()?.let { event ->
            when (event) {
                is SearchUiState.Event.SearchFailure -> {
                    val errorMessage = ErrorMessage.from(event.e)
                    ErrorDialog(
                        title = errorMessage.getTitle(),
                        message = errorMessage.getMessage(LocalContext.current),
                        onConfirm = { viewModel.consumeEvent(event) },
                    )
                }
            }
        }
    }
}
