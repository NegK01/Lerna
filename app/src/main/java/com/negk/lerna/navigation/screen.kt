package com.negk.lerna.navigation

sealed class Screen(val route: String) {
    data object Home : Screen("home")
    data object Games : Screen("games")
    data object Tests : Screen("tests")
    data object Profile : Screen("profile")

    companion object {
        val all = listOf(Home, Games, Tests, Profile)
    }
}