package com.workmate.workmatetz.presentation.navigation

sealed class Destination(val route: String) {
    object Home : Destination("home_screen")
    object Details : Destination("details/{seed}") {
        fun createRoute(seed: String) = "details/$seed"
    }

    object GenerateUsersList : Destination("gen_users_list?gender={gender}&nat={nat}") {
        fun createRoute(gender: String, nat: String) =
            "gen_users_list?gender=$gender&nat=$nat"
    }

    object ExistingUsers : Destination("existing_users")
}