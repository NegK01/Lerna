package com.negk.lernaminigames.feature.game

import androidx.compose.material3.*
import androidx.compose.runtime.Composable

@Composable
fun ResultDialog(
    title: String,
    message: String,
    onRetry: () -> Unit,
    onExitToPreGame: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(title) },
        text = { Text(message) },
        confirmButton = {
            TextButton(onClick = onRetry) { Text("Reintentar") }
        },
        dismissButton = {
            TextButton(onClick = onExitToPreGame) { Text("Volver") }
        }
    )
}
