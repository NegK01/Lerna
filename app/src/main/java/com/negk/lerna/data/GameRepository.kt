package com.negk.lerna.data

import com.negk.lerna.ui.game.memory.MemoryMatrixStateDao
import com.negk.lerna.ui.game.memory.MemoryMatrixStateEntity
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
}
