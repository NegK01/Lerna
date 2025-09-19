package com.negk.lerna.data

import android.content.Context
import com.negk.lerna.data.db.AppDatabase
import com.negk.lerna.data.source.AssetGameJsonProvider
import com.negk.lerna.data.source.GameJsonProvider
import com.negk.lerna.data.repository.GameRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

/**
 * Un localizador de servicios simple para proveer dependencias.
 */
object Graph {
    private lateinit var database: AppDatabase
    private lateinit var applicationContext: Context
    private val applicationScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    private val gameJsonProvider: GameJsonProvider by lazy {
        AssetGameJsonProvider(applicationContext)
    }

    val gameRepository: GameRepository by lazy {
        GameRepository(
            gameDao = database.gameDao(),
            memoryMatrixStateDao = database.memoryMatrixStateDao(),
            gameJsonProvider = gameJsonProvider
        )
    }

    fun provide(context: Context) {
        applicationContext = context.applicationContext
        database = AppDatabase.getDatabase(applicationContext, applicationScope)
    }
}