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
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(onClick = onExitClick) {
                Text(text = "Salir")
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Contenido principal del juego
            gameContent()

            Spacer(modifier = Modifier.height(5.dp))

            // Barra de progreso / HUD superior
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Nivel ${(levelProgress * 100).toInt()}%", style = MaterialTheme.typography.titleMedium)
            }
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

