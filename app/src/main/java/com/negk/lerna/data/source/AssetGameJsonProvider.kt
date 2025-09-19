package com.negk.lerna.data.source

import android.content.Context

/**
 * Implementación de [GameJsonProvider] que lee el JSON desde la carpeta de assets de Android.
 */
class AssetGameJsonProvider(private val context: Context) : GameJsonProvider {
    override suspend fun getGamesJsonString(): String {
        return context.assets.open("games.json").bufferedReader().use { it.readText() }
    }
}