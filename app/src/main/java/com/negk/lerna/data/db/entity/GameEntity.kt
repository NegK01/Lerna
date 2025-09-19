package com.negk.lerna.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "games")
data class GameEntity(
    @PrimaryKey val id: String,
    val title: String,
    val description: String,
    val cognitiveArea: String,
    val instructions: String,
    val hasLevels: Boolean
)