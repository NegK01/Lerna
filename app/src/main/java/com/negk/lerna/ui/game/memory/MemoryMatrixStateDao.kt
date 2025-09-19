package com.negk.lerna.ui.game.memory

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MemoryMatrixStateDao {
    @Query("SELECT * FROM memory_matrix_state WHERE id = :id")
    suspend fun getState(id: String = "default_state"): MemoryMatrixStateEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveState(state: MemoryMatrixStateEntity)
}