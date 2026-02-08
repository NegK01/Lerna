@file:OptIn(ExperimentalMaterial3Api::class)

package com.negk.lernaminigames.feature.game

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material3.TopAppBar

/**
 * Pantalla del juego (demo)
 *
 * Recibe:
 * - gameId: qué juego se está jugando (tap, memory, etc.)
 * - onExitToPreGame: callback para salir (lo implementa AppNavHost con nav.popBackStack())
 */
@Composable
fun GameScreen(
    gameId: String,
    onExitToPreGame: () -> Unit
) {
    // Estado del puntaje (se mantiene mientras esta pantalla viva)
    var score by remember { mutableIntStateOf(0) }
    // Controla si mostramos el dialog del resultado
    var showResult by remember { mutableStateOf(false) }

    // Función local para reiniciar los estados del juego
    fun reset() {
        score = 0
        showResult = false
    }

    Scaffold(
        topBar = {
            // TopAppBar (Material 3)
            TopAppBar(
                title = {
                    // Muestra el texto del juego actual, replazando la primera letra a una mayuscula
                    Text(
                        gameId.replaceFirstChar { char ->
                            if (char.isLowerCase()) char.titlecase()
                            else char.toString()
                        }
                    )
                },
                // Botón de flecha para salir, ubicado arriba a la izquierda
                navigationIcon = { TextButton(onClick = onExitToPreGame) { Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null) } }
            )
        }
    ) { padding ->
        // Box para centrar contenido
        Box(
            modifier = Modifier
                .padding(padding) // padding del Scaffold (evita que se tape con top bar)
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("Puntaje: $score", style = MaterialTheme.typography.headlineSmall)
                Spacer(Modifier.height(12.dp))

                // Sumar puntaje (desactivado cuando ya se esta mostrando resultado)
                Button(onClick = { score++ }, enabled = !showResult) { Text("+1") }
                Spacer(Modifier.height(12.dp))

                // Terminar la partida: muestra resultado flotante (Dialog)
                Button(onClick = { showResult = true }) { Text("Terminar") }
            }
        }
    }

    // Overlay: si mostrarResultado es true, dibujamos el ResultDialog encima.
    if (showResult) {
        // ResultDialog viene de otro archivo (ResultDialog.kt)
        ResultDialog(
            title = "Resultado",
            message = "Puntaje final: $score",
            // Reintentar: reinicia estado local
            onRetry = { reset() },
            // Volver: llama al callback (lo maneja NavHost con popBackStack())
            onExitToPreGame = { onExitToPreGame() },
            // Si se toca fuera del dialog, este callback se ejecuta
            onDismiss = { /* opcional: no hacer nada o cerrar */ }
        )
    }
}
