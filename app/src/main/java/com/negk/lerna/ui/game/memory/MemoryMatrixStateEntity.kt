package com.negk.lerna.ui.game.memory

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "memory_matrix_state")
data class MemoryMatrixStateEntity(
    @PrimaryKey val id: String = "default_state", // Solo un estado por ahora
    val sequence: String // Guardado como string separado por comas
)