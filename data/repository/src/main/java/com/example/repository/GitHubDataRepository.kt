package com.example.repository

import com.example.model.Repository
import com.example.source.api.GitHubApi
import com.example.source.api.handleApi
import javax.inject.Inject

class GitHubDataRepository @Inject constructor(private val api: GitHubApi) :
    GitHubRepository {
    private var cache = emptyList<Repository>()

    override fun getRepositoryInMemory(id: Int): Repository? {
        return cache.find { id == it.id }
    }

    override suspend fun search(keyword: String): Result<List<Repository>> {
        val result = handleApi { api.searchRepositories(keyword) }
        return if (result.isSuccess) {
            val response = result.getOrNull()!!
            cache = response.items
            Result.success(response.items)
        } else {
            Result.failure(result.exceptionOrNull()!!)
        }
    }
}
