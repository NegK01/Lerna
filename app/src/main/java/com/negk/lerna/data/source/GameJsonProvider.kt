package com.negk.lerna.data.source

/**
 * Interfaz que abstrae la fuente de datos del JSON de juegos.
 */
interface GameJsonProvider {
    suspend fun getGamesJsonString(): String
}