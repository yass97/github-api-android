package com.example.search.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.model.Repository

@Composable
fun RepositoryList(
    modifier: Modifier = Modifier,
    repositories: List<Repository>,
    onClick: (Int) -> Unit,
) {
    LazyColumn(modifier = modifier) {
        items(repositories) { repository ->
            Card(modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)) {
                Column(modifier = Modifier.clickable { onClick(repository.id) }) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        AsyncImage(
                            modifier = Modifier
                                .clip(CircleShape)
                                .size(40.dp)
                                .background(Color.White),
                            model = repository.owner.avatarUrl,
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Column(verticalArrangement = Arrangement.Center) {
                            Text(
                                text = repository.name,
                                style = MaterialTheme.typography.h6,
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Row {
                                Text(
                                    text = "Star ${repository.stargazersCount}",
                                    style = MaterialTheme.typography.caption,
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "Fork ${repository.forksCount}",
                                    style = MaterialTheme.typography.caption,
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        modifier = Modifier.padding(start = 62.dp, end = 8.dp),
                        text = repository.description ?: "No description",
                        maxLines = 2,
                        minLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.body2,
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                }
            }
        }
    }
}
