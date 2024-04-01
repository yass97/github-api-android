package com.example.githubapi.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.search.SearchScreen
import com.example.search.detail.RepositoryDetailScreen

@Composable
fun AppNavHost(controller: NavHostController = rememberNavController()) {
    NavHost(
        navController = controller,
        startDestination = Search.route,
    ) {
        composable(route = Search.route) {
            SearchScreen(onItemClick = { id ->
                controller.navigateToSingleTop("${Detail.route}/$id")
            })
        }
        composable(
            route = Detail.routeWithArgs,
            arguments = Detail.arguments,
        ) {
            val repoId = it.arguments?.getInt(Detail.REPOSITORY_ID)!!
            RepositoryDetailScreen(
                repositoryId = repoId,
                onBackClick = { controller.popBackStack() },
            )
        }
    }
}

fun NavHostController.navigateToSingleTop(route: String) {
    navigate(route) {
        popUpTo(graph.startDestinationId) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}
