package com.negk.lerna.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.negk.lerna.data.db.Converters
import com.negk.lerna.data.db.dao.GameDao
import com.negk.lerna.data.db.entity.GameEntity
import com.negk.lerna.data.db.dao.MemoryMatrixStateDao
import com.negk.lerna.data.db.entity.MemoryMatrixStateEntity
import com.negk.lerna.data.util.JsonDataPopulator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [GameEntity::class, MemoryMatrixStateEntity::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
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
                    // Poblar la base de datos usando el método centralizado
                    JsonDataPopulator.populateGames(context, database.gameDao())
                }
            }
        }
    }
}