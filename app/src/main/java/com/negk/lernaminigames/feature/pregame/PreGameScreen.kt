@file:OptIn(ExperimentalMaterial3Api::class)

package com.negk.lernaminigames.feature.pregame

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun PreGameScreen(
    gameId: String,
    onPlay: () -> Unit,
    onBack: () -> Unit
) {
    val title = when (gameId) {
        "tap" -> "Tap rápido"
        "memory" -> "Memoria"
        else -> "Juego"
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(title) },
                navigationIcon = { TextButton(onClick = onBack) { Text("Atrás") } }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text("Detalles y stats del juego ($gameId).", style = MaterialTheme.typography.titleMedium)
            Text("Aquí después pondremos: récord, configuración, dificultad, etc.")

            Spacer(Modifier.weight(1f))

            Button(
                onClick = onPlay,
                modifier = Modifier.fillMaxWidth()
            ) { Text("Jugar") }
        }
    }
}
