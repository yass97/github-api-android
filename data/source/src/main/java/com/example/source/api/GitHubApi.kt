package com.example.source.api

import com.example.source.api.response.RepositorySearchResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GitHubApi {
    @GET("search/repositories")
    suspend fun searchRepositories(@Query("q") keyword: String): Response<RepositorySearchResponse>
}
