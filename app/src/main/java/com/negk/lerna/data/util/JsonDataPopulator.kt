package com.negk.lerna.data.util

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.negk.lerna.data.db.dao.GameDao
import com.negk.lerna.data.db.entity.GameEntity
import com.negk.lerna.data.model.GameJson

/**
 * Objeto de utilidad para poblar la base de datos desde archivos JSON.
 */
object JsonDataPopulator {

    /**
     * Lee el archivo games.json de los assets, lo parsea y lo inserta en la base de datos.
     * Esta función puede ser utilizada tanto para la población inicial como para la sincronización.
     *
     * @param context Contexto de la aplicación para acceder a los assets.
     * @param gameDao El DAO para insertar los juegos en la base de datos.
     */
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