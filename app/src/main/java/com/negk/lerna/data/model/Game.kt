package com.negk.lerna.data.model

data class Game(
    val id: String,
    val title: String,
    val description: String,
    val hasLevels: Boolean,
    val levels: List<String>,
    val difficulty: String
)