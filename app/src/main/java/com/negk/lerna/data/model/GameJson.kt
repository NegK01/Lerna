package com.negk.lerna.data.model

data class GameJson(
    val id: String,
    val title: String,
    val description: String,
    val cognitiveArea: String,
    val instructions: String,
    val hasLevels: Boolean
)