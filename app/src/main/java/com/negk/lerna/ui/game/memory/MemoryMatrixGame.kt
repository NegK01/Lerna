package com.negk.lerna.ui.game.memory

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.negk.lerna.ui.game.GameHUDBase
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun MemoryMatrixGame(viewModel: MemoryMatrixViewModel = viewModel()) {
    val score by viewModel.score.collectAsState()
    val showLevelComplete by viewModel.levelComplete.collectAsState()

    GameHUDBase(
        gameContent = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
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
        onExitClick = { /* acción de salir */ }
    )
}
