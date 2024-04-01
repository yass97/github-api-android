package com.example.githubapi

import androidx.compose.runtime.Composable
import com.example.githubapi.navigation.AppNavHost
import com.example.githubapi.ui.theme.GithubApiTheme

@Composable
fun GitHubApp() {
    GithubApiTheme {
        AppNavHost()
    }
}
