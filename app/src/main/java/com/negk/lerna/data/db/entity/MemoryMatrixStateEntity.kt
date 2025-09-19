package com.negk.lerna.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "memory_matrix_state")
data class MemoryMatrixStateEntity(
    @PrimaryKey val id: String = "default_state", // Solo un estado por ahora
    val sequence: List<Int> // Guardado usando un TypeConverter
)