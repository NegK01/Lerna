package com.negk.lerna.data

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.negk.lerna.data.db.dao.GameDao
import com.negk.lerna.data.db.entity.GameEntity
import com.negk.lerna.data.db.dao.MemoryMatrixStateDao
import com.negk.lerna.data.db.entity.MemoryMatrixStateEntity
import com.negk.lerna.data.model.Game
import com.negk.lerna.data.model.GameJson
import com.negk.lerna.data.util.JsonDataPopulator
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

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
        // El TypeConverter ya nos da una List<Int>. Simplemente la retornamos.
        return state?.sequence ?: emptyList()
    }

    suspend fun saveMemoryMatrixSequence(sequence: List<Int>) {
        memoryMatrixStateDao.saveState(MemoryMatrixStateEntity(sequence = sequence))
    }

    suspend fun clearMemoryMatrixSequence() {
        memoryMatrixStateDao.saveState(MemoryMatrixStateEntity(sequence = emptyList()))
    }

    /**
     * Sincroniza la tabla de juegos en la base de datos con el archivo games.json de los assets.
     * Esto asegura que los datos de los juegos se actualicen después de una actualización de la app.
     */
    suspend fun syncGamesFromJson(context: Context) {
        JsonDataPopulator.populateGames(context, gameDao)
    }
}
