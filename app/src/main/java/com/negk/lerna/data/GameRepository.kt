package com.negk.lerna.data

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.InputStreamReader

data class GameJson(
    val id: String,
    val title: String,
    val description: String,
    val cognitiveArea: String,
    val instructions: String,
    val hasLevels: Boolean
)

data class Game(
    val id: String,
    val title: String,
    val description: String,
    val hasLevels: Boolean,
    val levels: List<String>,
    val difficulty: String
)

object GameRepository {
    fun getGameById(context: Context, id: String): Game {
        val inputStream = context.assets.open("games.json")
        val reader = InputStreamReader(inputStream)
        val gameListType = object : TypeToken<List<GameJson>>() {}.type
        val games: List<GameJson> = Gson().fromJson(reader, gameListType)
        reader.close()

        val gameJson = games.find { it.id == id }
            ?: throw IllegalArgumentException("Juego no encontrado")

        val levels = if (gameJson.hasLevels) listOf("Nivel 1", "Nivel 2", "Nivel 3") else emptyList()

        return Game(
            id = gameJson.id,
            title = gameJson.title,
            description = gameJson.description,
            hasLevels = gameJson.hasLevels,
            levels = levels,
            difficulty = "Fácil"
        )
    }
}
