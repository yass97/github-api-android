package com.example.search.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Lock
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.search.detail.extension.formatDateTime

@Composable
fun RepositoryDetailScreen(
    repositoryId: Int,
    viewModel: RepositoryDetailViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()
    val uriHandler = LocalUriHandler.current

    LaunchedEffect(Unit) {
        viewModel.getRepositoryInMemory(repositoryId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Repository Detail") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = null,
                        )
                    }
                },
            )
        },
    ) { paddingValue ->
        uiState.repository?.let {
            Column(
                modifier = Modifier
                    .padding(paddingValue)
                    .verticalScroll(rememberScrollState()),
            ) {
                Column(modifier = Modifier.padding(10.dp)) {
                    OwnerSection(
                        avatarUrl = it.owner.avatarUrl,
                        ownerName = it.owner.login,
                        onClick = { uriHandler.openUri(it.owner.htmlUrl) },
                    )
                    Text(
                        modifier = Modifier.padding(vertical = 6.dp),
                        text = it.name,
                        style = MaterialTheme.typography.h5,
                    )
                    if (it.private) {
                        Row(
                            modifier = Modifier.padding(bottom = 2.dp),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Icon(
                                modifier = Modifier.size(18.dp),
                                imageVector = Icons.Default.Lock,
                                contentDescription = null,
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(text = "Private")
                        }
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    CountsSection(
                        starCounts = it.stargazersCount,
                        forkCounts = it.forksCount,
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .fillMaxWidth()
                            .background(Color.LightGray.copy(alpha = 0.4f)),
                    ) {
                        Text(
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 8.dp),
                            text = it.description ?: "No description",
                            style = MaterialTheme.typography.body2,
                            minLines = 2,
                        )
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Divider()
                    Spacer(modifier = Modifier.height(10.dp))
                    TextDetailSection(title = "Language", detail = it.language)
                    Spacer(modifier = Modifier.height(10.dp))
                    TextDetailSection(title = "Topics", detail = it.topics.joinToString())
                    Spacer(modifier = Modifier.height(10.dp))
                    LinkSection(
                        title = "Homepage",
                        detail = it.homepage,
                        onClick = { uriHandler.openUri(it) },
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    TextDetailSection(title = "License", detail = it.license?.name)
                    Spacer(modifier = Modifier.height(10.dp))
                    TextDetailSection(
                        title = "Date created",
                        detail = it.createdAt.formatDateTime(),
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    TextDetailSection(
                        title = "Date updated",
                        detail = it.updatedAt.formatDateTime(),
                    )
                }
            }
        } ?: Text(text = "Repository not found")
    }
}

@Composable
private fun OwnerSection(
    modifier: Modifier = Modifier,
    avatarUrl: String,
    ownerName: String,
    onClick: () -> Unit,
) {
    Row(
        modifier = modifier.clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        AsyncImage(
            modifier = Modifier
                .clip(CircleShape)
                .size(30.dp)
                .background(Color.White),
            model = avatarUrl,
            contentDescription = null,
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = ownerName)
    }
}

@Composable
private fun CountsSection(
    modifier: Modifier = Modifier,
    starCounts: Int,
    forkCounts: Int,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = starCounts.toString(),
                fontWeight = FontWeight.Bold,
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(text = "Star")
        }
        Spacer(modifier = Modifier.width(10.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = forkCounts.toString(),
                fontWeight = FontWeight.Bold,
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(text = "Fork")
        }
    }
}

@Composable
private fun LinkSection(
    modifier: Modifier = Modifier,
    title: String,
    detail: String?,
    onClick: (String) -> Unit,
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = title,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.subtitle1,
        )
        Spacer(modifier = Modifier.height(4.dp))
        if (detail.isNullOrBlank()) {
            Text(
                text = "-",
                style = MaterialTheme.typography.body2,
            )
        } else {
            ClickableText(
                text = buildAnnotatedString {
                    withStyle(
                        SpanStyle(
                            color = MaterialTheme.colors.onSurface,
                            textDecoration = TextDecoration.Underline,
                        ),
                    ) {
                        append(detail)
                    }
                },
                onClick = { onClick(detail) },
                style = MaterialTheme.typography.body2,
            )
        }
    }
}

@Composable
private fun TextDetailSection(
    modifier: Modifier = Modifier,
    title: String,
    detail: String?,
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = title,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.subtitle1,
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = if (detail.isNullOrBlank()) "-" else detail,
            style = MaterialTheme.typography.body2,
        )
    }
}
