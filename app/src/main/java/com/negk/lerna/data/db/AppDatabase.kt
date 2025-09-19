package com.negk.lerna.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.negk.lerna.data.db.dao.GameDao
import com.negk.lerna.data.db.entity.GameEntity
import com.negk.lerna.data.db.dao.MemoryMatrixStateDao
import com.negk.lerna.data.db.entity.MemoryMatrixStateEntity
import com.negk.lerna.data.model.GameJson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [GameEntity::class, MemoryMatrixStateEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun gameDao(): GameDao
    abstract fun memoryMatrixStateDao(): MemoryMatrixStateDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "lerna_database"
                ).addCallback(DatabaseCallback(context, scope)).build()
                INSTANCE = instance
                instance
            }
        }
    }

    private class DatabaseCallback(
        private val context: Context,
        private val scope: CoroutineScope
    ) : Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch(Dispatchers.IO) {
                    populateGames(context, database.gameDao())
                }
            }
        }

        suspend fun populateGames(context: Context, gameDao: GameDao) {
            // 1. Leer el archivo JSON desde assets
            val gamesJsonString = context.assets.open("games.json").bufferedReader().use { it.readText() }

            // 2. Parsear el JSON a una lista de objetos GameJson
            val gameListType = object : TypeToken<List<GameJson>>() {}.type
            val gamesList: List<GameJson> = Gson().fromJson(gamesJsonString, gameListType)

            // 3. Mapear de GameJson a GameEntity
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

            // 4. Insertar los datos en la base de datos
            gameDao.insertAll(gameEntities)
        }
    }
}