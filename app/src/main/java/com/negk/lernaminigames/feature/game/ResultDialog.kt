package com.negk.lernaminigames.feature.game

import androidx.compose.material3.*
import androidx.compose.runtime.Composable

/**
 * Dialog flotante de resultado
 *
 * Lo usamos como "overlay" encima del juego en vez de otra pantalla,
 * porque para MVP es más simple y se siente natural:
 * - Reintentar : reinicia estados
 * - Volver     : sale al pre-juego
 */
@Composable
fun ResultDialog(
    title: String,
    message: String,
    onRetry: () -> Unit,
    onExitToPreGame: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        // Se ejecuta si el usuario toca fuera del dialog o presiona "atrás" del sistema.
        onDismissRequest = onDismiss,

        title = { Text(title) },
        text = { Text(message) },

        // Botón principal (acción positiva)
        confirmButton = {
            TextButton(onClick = onRetry) { Text("Reintentar") }
        },
        // Botón secundario (acción negativa / volver)
        dismissButton = {
            TextButton(onClick = onExitToPreGame) { Text("Volver") }
        }
    )
}
