package com.negk.lernaminigames.core.navigation

/**
 * Este archivo define TODAS las rutas (pantallas) que existen en la app
 *
 * Por qué existe?
 * - Para NO tener strings sueltos por el proyecto ("home", "game/tap", etc.)
 * - Para centralizar y evitar errores de tipeo
 *
 * sealed class:
 * - Significa "lista cerrada": solo existen las rutas que declaramos aquí
 * - Eso hace el proyecto más seguro y fácil de mantener
 */
sealed class Route(val path: String) {
    // Bottom tabs
    // -------------------------
    // 1) Pantallas raíz (tabs)
    // -------------------------
    // Estas son las pantallas que aparecen en el menú inferior (BottomBar).
    data object Home : Route("home")
    data object Profile : Route("profile")

    // Flow del juego
    // -------------------------
    // 2) Flujo interno del juego
    // -------------------------
    /**
     * PreJuego usa un argumento dinamico {idJuego}
     * - El patrón (patron) se usa para "registrar" la ruta en NavHost
     * - Y luego, cuando navegamos, usamos crear(id) para construir la ruta real
     *
     * Ej:
     * patron: "pregame/{idJuego}"
     * ruta real: "pregame/tap"
     */
    data object PreGame : Route("pregame/{gameId}") {
        fun create(gameId: String) = "pregame/$gameId"
    }
    /**
     * Mismo concepto para Game
     */
    data object Game : Route("game/{gameId}") {
        fun create(gameId: String) = "game/$gameId"
    }
}

/**
 * Lista controlada de IDs de juego
 *
 * - Evitamos que un error mecanografico del id del juego genere un bug silencioso
 */
enum class GameId(val id: String) {
    Tap("tap"),
    Memory("memory");

    companion object {
        /**
         * Convierte un string a IdJuego si existe
         * Si no existe devuelve null
         *
         * entries = lista de todos los valores del enum (Tap, Memoria)
         */
        fun from(id: String?): GameId? = entries.firstOrNull { it.id == id }
    }
}