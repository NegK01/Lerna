package com.negk.lerna.data.db

import androidx.room.TypeConverter

/**
 * Type converters para permitir que Room almacene tipos de datos complejos.
 */
class Converters {
    @TypeConverter
    fun fromString(value: String): List<Int> {
        return if (value.isEmpty()) emptyList() else value.split(",").mapNotNull { it.toIntOrNull() }
    }

    @TypeConverter
    fun fromList(list: List<Int>): String {
        return list.joinToString(",")
    }
}