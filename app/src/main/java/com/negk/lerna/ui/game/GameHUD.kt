package com.negk.lerna.ui.game

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable

sealed class HUDMode {
    object None : HUDMode()
    data class LevelProgress(val progress: Float) : HUDMode() // 0f..1f porcentaje de progreso
    data class Timer(
        val totalTimeMs: Long, // Duracion total del temporizador en milisegundos
        val onFinish: () -> Unit = {} // Realizar una accion cuando el temporizador llega a 0
    ) : HUDMode()
}

@Composable
fun GameHUD(
    gameContent: @Composable () -> Unit,
    hudMode: HUDMode = HUDMode.None,
    showLevelComplete: Boolean = false,
    onDismissLevelComplete: () -> Unit = {},
    onExitClick: () -> Unit = {}
) {
    // Animatable para controlar la barra de progreso del temporizador
    var timerAnim = remember { Animatable(1f) }

    // Inicia la animación lineal del temporizador cuando se activa el modo Timer
    LaunchedEffect(hudMode) {
        if (hudMode is HUDMode.Timer) {
            timerAnim.snapTo(1f) // Reinicia la animación al 100%
            timerAnim.animateTo(
                targetValue = 0f, // Anima hasta 0% al finalizar
                animationSpec = tween(durationMillis = hudMode.totalTimeMs.toInt(), easing = LinearEasing)
            )
            hudMode.onFinish() // Ejecuta acción al llegar a 0
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp, 0.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(onClick = onExitClick, Modifier.width(60.dp)) { Text("x") }
                Spacer(modifier = Modifier.width(8.dp))

                when (hudMode) {
                    is HUDMode.LevelProgress -> {
                        CleanLinearProgress(
                            progress = hudMode.progress,
                            heightDp = 8f
                        )

                    }
                    is HUDMode.Timer -> {
                        // Muestra la barra de progreso animada del temporizador
                        CleanLinearProgress(
                            progress = timerAnim.value,
                            heightDp = 8f
                        )
                    }
                    else -> {}
                }
            }

            // Contenido del juego
            gameContent()
        }

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
            .background(Color(0x88000000))
            .clickable { onDismiss() },
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .background(Color.White)
                .padding(24.dp)
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("¡Felicidades!", style = MaterialTheme.typography.titleLarge)
                Spacer(Modifier.height(8.dp))
                Text("Nivel completado", style = MaterialTheme.typography.bodyMedium)
                Spacer(Modifier.height(16.dp))
                Button(onClick = onDismiss) { Text("Continuar") }
            }
        }
    }
}

@Composable
fun CleanLinearProgress(
    progress: Float, // 0f..1f
    modifier: Modifier = Modifier,
    trackColor: Color = MaterialTheme.colorScheme.tertiary.copy(alpha = 0.3f),
    progressColor: Color = MaterialTheme.colorScheme.secondary,
    heightDp: Float = 8f,
    cornerRadiusDp: Float = 4f
) {
    Canvas(modifier = modifier
        .fillMaxWidth()
        .height(heightDp.dp)
    ) {
        val width = size.width
        val height = size.height
        val cornerRadiusPx = cornerRadiusDp * density

        // Dibujar track de fondo
        drawRoundRect(
            color = trackColor,
            size = size,
            cornerRadius = androidx.compose.ui.geometry.CornerRadius(cornerRadiusPx, cornerRadiusPx)
        )

        // Dibujar progreso
        drawRoundRect(
            color = progressColor,
            size = androidx.compose.ui.geometry.Size(width * progress, height),
            cornerRadius = androidx.compose.ui.geometry.CornerRadius(cornerRadiusPx, cornerRadiusPx)
        )
    }
}