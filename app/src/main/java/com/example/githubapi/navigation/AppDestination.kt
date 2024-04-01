package com.example.githubapi.navigation

import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed interface AppDestination {
    val route: String
}

data object Search : AppDestination {
    override val route = "search"
}

data object Detail : AppDestination {
    override val route: String = "detail"
    const val REPOSITORY_ID = "repositoryId"
    val routeWithArgs = "$route/{$REPOSITORY_ID}"
    val arguments = listOf(
        navArgument(REPOSITORY_ID) { type = NavType.IntType },
    )
}
