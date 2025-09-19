package com.negk.lerna.data

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.negk.lerna.data.db.GameDao
import com.negk.lerna.data.db.GameEntity
import com.negk.lerna.data.db.MemoryMatrixStateDao
import com.negk.lerna.data.db.MemoryMatrixStateEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// Esta clase ya no es necesaria, la información se mapea desde GameEntity
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

class GameRepository(
    private val gameDao: GameDao,
    private val memoryMatrixStateDao: MemoryMatrixStateDao
) {
    fun getGameById(id: String): Flow<Game?> {
        return gameDao.getGameById(id).map { gameEntity ->
            gameEntity?.let {
                val levels = if (it.hasLevels) listOf("Nivel 1", "Nivel 2", "Nivel 3") else emptyList()
                Game(
                    id = it.id,
                    title = it.title,
                    description = it.description,
                    hasLevels = it.hasLevels,
                    levels = levels,
                    difficulty = "Fácil"
                )
            }
        }
    }

    suspend fun loadMemoryMatrixSequence(): List<Int> {
        val state = memoryMatrixStateDao.getState()
        return if (state != null && state.sequence.isNotEmpty()) {
            state.sequence.split(",").mapNotNull { it.toIntOrNull() }
        } else {
            emptyList()
        }
    }

    suspend fun saveMemoryMatrixSequence(sequence: List<Int>) {
        val sequenceString = sequence.joinToString(",")
        memoryMatrixStateDao.saveState(MemoryMatrixStateEntity(sequence = sequenceString))
    }

    suspend fun clearMemoryMatrixSequence() {
        memoryMatrixStateDao.saveState(MemoryMatrixStateEntity(sequence = ""))
    }

    /**
     * Sincroniza la tabla de juegos en la base de datos con el archivo games.json de los assets.
     * Esto asegura que los datos de los juegos se actualicen después de una actualización de la app.
     */
    suspend fun syncGamesFromJson(context: Context) {
        val gamesJsonString = context.assets.open("games.json").bufferedReader().use { it.readText() }
        val gameListType = object : TypeToken<List<GameJson>>() {}.type
        val gamesList: List<GameJson> = Gson().fromJson(gamesJsonString, gameListType)

        val gameEntities = gamesList.map { gameJson ->
            GameEntity(
                id = gameJson.id,
                title = gameJson.title,
                description = gameJson.description,
                cognitiveArea = gameJson.cognitiveArea,
                instructions = gameJson.instructions,
                hasLevels = gameJson.hasLevels
            )
        }

        // Usando insertAll con OnConflictStrategy.REPLACE, se actualizarán los juegos
        // existentes y se insertarán los nuevos.
        gameDao.insertAll(gameEntities)
    }
}
