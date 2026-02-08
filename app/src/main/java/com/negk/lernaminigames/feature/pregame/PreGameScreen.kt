@file:OptIn(ExperimentalMaterial3Api::class)

package com.negk.lernaminigames.feature.pregame

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Pantalla "PreJuego":
 * - Se ve antes de jugar
 * - Aquí van stats (records, etc.) y configuracion (dificultad, etc.)
 */
@Composable
fun PreGameScreen(
    gameId: String,
    onPlay: () -> Unit,
    onBack: () -> Unit
) {
    // Elegimos un titulo segun el id
    val title = when (gameId) {
        "tap" -> "Tap rápido"
        "memory" -> "Memoria"
        else -> "Juego"
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(title) },
                // Botón atrás de la barra superior
                navigationIcon = { TextButton(onClick = onBack) { Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null) } }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding) // padding del Scaffold (top bar)
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text("Detalles y stats del juego ($gameId).", style = MaterialTheme.typography.titleMedium)
            Text("Aquí después pondremos: récord, configuración, dificultad, etc.")

            // Empuja el botón al final con un spacio vacio
            Spacer(Modifier.weight(1f))

            Button(
                onClick = onPlay,
                modifier = Modifier.fillMaxWidth()
            ) { Text("Jugar") }
        }
    }
}
