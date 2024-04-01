package com.example.repository

import com.example.model.Repository

interface GitHubRepository {
    fun getRepositoryInMemory(id: Int): Repository?
    suspend fun search(keyword: String): Result<List<Repository>>
}
