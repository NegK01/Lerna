package com.negk.lerna.ui.screens.pre_game

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.negk.lerna.R
import com.negk.lerna.data.Game
import com.negk.lerna.data.gameDrawableMap

@Composable
fun PreGameScreen(game: Game, modifier: Modifier = Modifier, onPlayClick: () -> Unit = {}) {
    val imageRes = game.id.let { id ->
        gameDrawableMap[id]
    } ?: R.drawable.memory_matrix

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        Image(
            painter = painterResource(id = imageRes),
            contentDescription = game.title,
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
        )

        Text(text = game.title, style = MaterialTheme.typography.titleLarge)
        Text(text = game.description, style = MaterialTheme.typography.bodyMedium)

        if (game.hasLevels) {
            Text(text = "Niveles", style = MaterialTheme.typography.titleMedium)
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(game.levels) { level ->
                    Button(onClick = { /* Seleccionar nivel */ }) {
                        Text(level)
                    }
                }
            }
        }

        Text(text = "Dificultad: ${game.difficulty}", fontSize = 14.sp)

        Button(
            onClick = onPlayClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text(text = "Jugar")
        }
    }
}
