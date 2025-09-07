package com.negk.lerna.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign

/**
 * Header (TopAppBar)
 * Componente de encabezado fijo para la app Lerna.
 *
 * Incluye:
 *   - Título alineado a la izquierda
 *   - Botón de racha
 *   - Botón de notificaciones
 *
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Header(
    modifier: Modifier = Modifier,
    onStreakClick: () -> Unit = {},
    onNotificationClick: () -> Unit = {}
) {
    TopAppBar(
        title = {
            Text(
                text = "Lerna",
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Start
            )
        },
        actions = {
            IconButton(onClick = onStreakClick) {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = "Racha"
                )
            }
            IconButton(onClick = onNotificationClick) {
                Icon(
                    imageVector = Icons.Default.Notifications,
                    contentDescription = "Notificaciones"
                )
            }
        },
        modifier = modifier,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    )
}
