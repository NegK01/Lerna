package com.negk.lerna.data

import android.content.Context
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob


/**
 * Un localizador de servicios simple para proveer dependencias.
 */
object Graph {
    private lateinit var database: AppDatabase
    private val applicationScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    val gameRepository: GameRepository by lazy {
        GameRepository(database.gameDao(), database.memoryMatrixStateDao())
    }

    fun provide(context: Context) {
        database = AppDatabase.getDatabase(context, applicationScope)
    }
}