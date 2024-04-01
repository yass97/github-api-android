package com.example.githubapi.di

import com.example.repository.GitHubDataRepository
import com.example.repository.GitHubRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Singleton
    @Binds
    abstract fun bindGitHubRepository(repository: GitHubDataRepository): GitHubRepository
}
