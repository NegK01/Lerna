package com.negk.lerna.ui.game

import android.text.style.BackgroundColorSpan
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.material3.LinearProgressIndicator

/**
 * GameHUDBase es la estructura base de cualquier juego.
 * Contiene:
 * - gameContent: el juego específico
 * - overlays/modals
 * - barra de progreso / HUD
 * - botones de salir o menú
 */
@Composable
fun GameHUDBase(
    gameContent: @Composable () -> Unit,
    showLevelComplete: Boolean = false,
    onDismissLevelComplete: () -> Unit = {},
    levelProgress: Float = 0f, // 0f .. 1f
    onExitClick: () -> Unit = {}
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Red.copy(alpha = 0.1f)), // Prueba, ver area de HUD real
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Barra de progreso / HUD superior
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Yellow.copy(alpha = 0.1f)) // Prueba, ver area de HUD real
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(onClick = onExitClick) {
                    Text(text = "X")
                }

                Spacer(modifier = Modifier.width(16.dp))

                LinearProgressIndicator(
                    progress = { levelProgress }, // usar mi Float (0f..1f)
                    modifier = Modifier
                        .weight(1f) // que se expanda
                        .height(8.dp), // grosor de la barra
                )
            }

            // Contenido principal del juego
            gameContent()
        }

        // Overlay modal nivel completado (encima de todo)
        if (showLevelComplete) {
            LevelCompleteModal(onDismiss = onDismissLevelComplete)
        }
    }
}


@Composable
fun LevelCompleteModal(onDismiss: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0x88000000)) // semitransparente
            .clickable { onDismiss() },
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .background(Color.White)
                .padding(24.dp)
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "¡Felicidades!", style = MaterialTheme.typography.titleLarge)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Nivel completado", style = MaterialTheme.typography.bodyMedium)
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = onDismiss) { Text("Continuar") }
            }
        }
    }
}

