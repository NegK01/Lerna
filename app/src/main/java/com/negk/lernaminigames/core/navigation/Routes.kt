package com.negk.lernaminigames.core.navigation

sealed class Route(val path: String) {
    // Bottom tabs
    data object Home : Route("home")
    data object Profile : Route("profile")

    // Flow del juego
    data object PreGame : Route("pregame/{gameId}") {
        fun create(gameId: String) = "pregame/$gameId"
    }
    data object Game : Route("game/{gameId}") {
        fun create(gameId: String) = "game/$gameId"
    }
}

enum class GameId(val id: String) {
    Tap("tap"),
    Memory("memory");

    companion object {
        fun from(id: String?): GameId? = entries.firstOrNull { it.id == id }
    }
}