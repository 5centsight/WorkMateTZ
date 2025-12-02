package com.workmate.workmatetz.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.workmate.workmatetz.presentation.screens.home.HomeScreen
import com.workmate.workmatetz.presentation.screens.userDetails.UserDetailsScreen
import com.workmate.workmatetz.presentation.screens.usersList.ExistingUsersListScreen
import com.workmate.workmatetz.presentation.screens.usersList.UsersListScreen

@Composable
fun AppNavGraph(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Destination.Home.route
    ) {

        composable(route = Destination.Home.route) {
            HomeScreen(
                onGenerateClick = { gender, nationality ->
                    navController.navigate(
                        Destination.GenerateUsersList.createRoute(gender, nationality)
                    )
                },
                onBackClick = {
                    navController.navigate(
                        Destination.ExistingUsers.route
                    )
                }
            )
        }

        composable(route = Destination.ExistingUsers.route) {
            ExistingUsersListScreen(
                onGenerateClick = { navController.navigate(Destination.Home.route) },
                onDetailsClick = { seed ->
                    navController.navigate(
                        Destination.Details.createRoute(seed)
                    )
                }
            )
        }

        composable(
            route = Destination.GenerateUsersList.route,
            arguments = listOf(
                navArgument("gender") {
                    type = NavType.StringType
                    defaultValue = "male"
                },
                navArgument("nat") {
                    type = NavType.StringType
                    defaultValue = "us"
                }
            )
        ) { backStackEntry ->
            val gender = backStackEntry.arguments?.getString("gender") ?: "male"
            val nationality = backStackEntry.arguments?.getString("nat") ?: "us"

            UsersListScreen(
                gender = gender,
                nationality = nationality,
                onGenerateClick = { navController.navigate(Destination.Home.route) },
                onDetailsClick = { seed ->
                    navController.navigate(
                        Destination.Details.createRoute(seed)
                    )
                }
            )
        }

        composable(
            route = Destination.Details.route,
            arguments = listOf(navArgument("seed") { type = NavType.StringType })
        ) { backStackEntry ->
            val seed = backStackEntry.arguments?.getString("seed") ?: ""

            UserDetailsScreen(
                seed = seed,
                onBackClick = { navController.navigateUp() },
            )
        }
    }
}