package com.negk.lerna.ui.game.memory

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.negk.lerna.ui.game.GameHUD
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.foundation.background
import androidx.compose.ui.graphics.Color

@Composable
fun MemoryMatrixGame(viewModel: MemoryMatrixViewModel = viewModel()) {
    val score by viewModel.score.collectAsState()
    val showLevelComplete by viewModel.levelComplete.collectAsState()

    GameHUD(
        gameContent = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .background(Color.Green.copy(alpha = 0.1f)), // Prueba, ver area de juego real
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(text = "Puntaje: $score")
                Button(onClick = { viewModel.incrementScore() }) {
                    Text("Tocar para sumar")
                }
            }
        },
        showLevelComplete = showLevelComplete,
        onDismissLevelComplete = { viewModel.dismissLevelComplete() },
        levelProgress = score / 5f,
        onExitClick = { /* acción de salir */ },
    )
}

