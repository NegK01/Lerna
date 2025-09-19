package com.negk.lerna.ui.game.memory

import android.app.Application
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.negk.lerna.ui.game.GameHUD
import com.negk.lerna.ui.game.HUDMode
import androidx.compose.animation.core.*
import androidx.compose.foundation.border
import androidx.compose.ui.draw.clip
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.animation.animateColorAsState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.window.Dialog
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MenuAnchorType.Companion.PrimaryNotEditable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

/**
 * Composable principal para el juego Memory Matrix.
 * Muestra la interfaz del juego con HUD, rejilla, controles y estado.
 */
@Composable
fun MemoryMatrixGame(
    viewModel: MemoryMatrixViewModel = viewModel(),
    onExit: () -> Unit
) {
    val gridSize by viewModel.gridSize.collectAsState()
    val sequence by viewModel.sequence.collectAsState()
    val isShowingSequence by viewModel.isShowingSequence.collectAsState()
    val currentLitCell by viewModel.currentLitCell.collectAsState()
    val sequenceCount by viewModel.sequenceCount.collectAsState() // Secuencia logradas
    val gameOver by viewModel.gameOver.collectAsState()
    val gameCompleted by viewModel.gameCompleted.collectAsState()
    val showAchieved by viewModel.showAchieved.collectAsState()

    GameHUD(
        gameContent = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // Mostrar contador de secuencias
                Text("Secuencia Actual: ${sequence.size}", color = MaterialTheme.colorScheme.onBackground)
                Text("Secuencias completadas: $sequenceCount", color = MaterialTheme.colorScheme.onBackground)

                Spacer(modifier = Modifier.height(16.dp))

                // Estado del juego
                Text(
                    text = when {
                        isShowingSequence -> "Observa la secuencia"
                        gameOver -> "¡Perdiste! Puntaje: $sequenceCount"
                        showAchieved -> "¡Logrado! 🎉"
                        gameCompleted -> "¡Juego completado!"
                        else -> "Repite la secuencia"
                    },
                    color = MaterialTheme.colorScheme.onBackground
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Rejilla de matriz
                MemoryMatrixGrid(
                    gridSize = gridSize,
                    currentLitCell = currentLitCell,
                    isShowingSequence = isShowingSequence,
                    onCellClick = { index -> viewModel.onCellClick(index) }
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Botón de iniciar/continuar
                Button(
                    onClick = {
                        if (gameOver || gameCompleted) viewModel.resetGame() else viewModel.startSequence()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    )
                ) {
                    Text(if (sequence.size == 0) "Iniciar Secuencia" else "Continuar Secuencia")
                }
            }
        },
        showLevelComplete = false, // No usar level complete
        onDismissLevelComplete = {},
        hudMode = HUDMode.None, // Sin barra de progreso
        onExitClick = onExit,
    )

    // Modal de game over
    if (gameOver) {
        GameOverModal(
            score = sequenceCount,
            onDismiss = { viewModel.resetGame() }
        )
    }

    // Modal de juego completado
    if (gameCompleted) {
        GameCompletedModal(
            onDismiss = { viewModel.resetGame() },
            winCount = MemoryMatrixViewModel.WIN_SEQUENCE_COUNT
        )
    }
}

/**
 * Selector de tamaño de rejilla para el juego.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GridSizeSelector(currentSize: Int, onSizeSelected: (Int) -> Unit) {
    val sizes = listOf(3, 4, 5)
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        Text(
            text = "Tamaño de rejilla: ${currentSize}x$currentSize",
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier
                .menuAnchor(PrimaryNotEditable)
                .clickable { expanded = true }
                .padding(8.dp)
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            sizes.forEach { size ->
                DropdownMenuItem(
                    text = { Text("${size}x$size") },
                    onClick = {
                        onSizeSelected(size)
                        expanded = false
                    }
                )
            }
        }
    }
}

/**
 * Rejilla de matriz para el juego Memory Matrix.
 */
@Composable
fun MemoryMatrixGrid(
    gridSize: Int,
    currentLitCell: Int?,
    isShowingSequence: Boolean,
    onCellClick: (Int) -> Unit
) {
    val totalCells = gridSize * gridSize
    val cellIndices = (0 until totalCells).toList()

    LazyVerticalGrid(
        columns = GridCells.Fixed(gridSize),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        items(cellIndices) { index ->
            val isLit = index == currentLitCell
            MemoryCell(isLit = isLit, isActive = !isShowingSequence, onClick = { onCellClick(index) })
        }
    }
}

/**
 * Celda individual de la matriz.
 */
@Composable
fun MemoryCell(isLit: Boolean, isActive: Boolean, onClick: () -> Unit) {
    val backgroundColor by animateColorAsState(
        targetValue = if (isLit) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant,
        animationSpec = tween(300) // Fade in/out rápido
    )

    Box(
        modifier = Modifier
            .aspectRatio(1f) // 👈 siempre cuadrado
            .background(backgroundColor, RoundedCornerShape(8.dp))
            .border(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.3f), RoundedCornerShape(8.dp))
            .clip(RoundedCornerShape(8.dp))
            .then(if (isActive) Modifier.clickable(onClick = onClick) else Modifier)
    )
}

/**
 * Modal para game over.
 */
@Composable
fun GameOverModal(score: Int, onDismiss: () -> Unit) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier.padding(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Game Over", style = MaterialTheme.typography.titleLarge)
                Spacer(Modifier.height(8.dp))
                Text("Puntaje: $score secuencias", style = MaterialTheme.typography.bodyMedium)
                Spacer(Modifier.height(16.dp))
                Button(onClick = onDismiss) { Text("Reiniciar") }
            }
        }
    }
}

/**
 * Modal para juego completado.
 */
@Composable
fun GameCompletedModal(winCount: Int, onDismiss: () -> Unit) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier.padding(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("¡Felicidades!", style = MaterialTheme.typography.titleLarge)
                Spacer(Modifier.height(8.dp))
                Text("Has completado el juego con $winCount secuencias", style = MaterialTheme.typography.bodyMedium)
                Spacer(Modifier.height(16.dp))
                Button(onClick = onDismiss) { Text("Reiniciar") }
            }
        }
    }
}