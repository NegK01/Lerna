@file:OptIn(ExperimentalMaterial3Api::class)

package com.negk.lernaminigames.feature.game

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material3.TopAppBar


@Composable
fun GameScreen(
    gameId: String,
    onExitToPreGame: () -> Unit
) {
    var score by remember { mutableIntStateOf(0) }
    var showResult by remember { mutableStateOf(false) }

    fun reset() {
        score = 0
        showResult = false
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Jugando: $gameId") },
                navigationIcon = { TextButton(onClick = onExitToPreGame) { Text("Salir") } }
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("Puntaje: $score", style = MaterialTheme.typography.headlineSmall)
                Spacer(Modifier.height(12.dp))

                Button(onClick = { score++ }, enabled = !showResult) { Text("+1") }
                Spacer(Modifier.height(12.dp))

                Button(onClick = { showResult = true }) { Text("Terminar") }
            }
        }
    }

    if (showResult) {
        ResultDialog(
            title = "Resultado",
            message = "Puntaje final: $score",
            onRetry = { reset() },
            onExitToPreGame = { onExitToPreGame() },
            onDismiss = { /* opcional: no hacer nada o cerrar */ }
        )
    }
}
