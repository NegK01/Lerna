package com.negk.lerna.ui.screens.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.res.painterResource
import com.negk.lerna.R
import com.negk.lerna.ui.components.BaseCard
import com.negk.lerna.ui.components.shimmerBackground
import com.negk.lerna.data.Game
import com.negk.lerna.data.Graph
import com.negk.lerna.data.gameDrawableMap

@Composable
fun RecommendedGameCard(
    modifier: Modifier = Modifier,
    height: Dp = 200.dp,
    gameId: String? = null,
    imageSize: Dp = 150.dp,
    onButtonClick: () -> Unit = {}
) {
    // 1. Obtener datos del juego de forma reactiva si se pasa gameId
    val game by produceState<Game?>(initialValue = null, gameId) {
        if (gameId != null) {
            Graph.gameRepository.getGameById(gameId).collect { value = it }
        }
    }

    BaseCard(
        modifier = modifier
            .height(height)
            .clickable(onClick = onButtonClick),
        backgroundColor = MaterialTheme.colorScheme.surface,
        elevation = 6.dp
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                if (game == null && gameId != null) { // Estado de carga (Skeleton)
                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.Center
                    ) {
                        Box(modifier = Modifier
							.fillMaxWidth(0.6f)
							.height(28.dp) // Aprox. titleLarge
							.shimmerBackground())
						Spacer(modifier = Modifier.height(8.dp))
						Box(modifier = Modifier
							.fillMaxWidth(0.8f)
							.height(20.dp) // Aprox. bodyMedium
							.shimmerBackground())
                    }
                    Box(modifier = Modifier
						.size(imageSize)
						.weight(1f)
						.padding(start = 50.dp)
						.shimmerBackground())
                } else if (game != null) {
                    // Contenido cargado
                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.Center
                    ) {
                        // 2. Mostrar título y descripción desde game
                        Text(
                            text = game!!.title,
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = game!!.description,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            textAlign = TextAlign.Start
                        )
                    }
                    // 3. Imagen desde gameId
                    val imageRes = game!!.id.let { id ->
                        gameDrawableMap[id]
                    } ?: R.drawable.memory_matrix // Recurso por defecto si no se encuentra
                    Image(
                        painter = painterResource(id = imageRes),
                        contentDescription = game!!.title,
                        modifier = Modifier
                            .size(imageSize)
                            .weight(1f)
                            .padding(start = 50.dp)
                    )
                }
            }
        }
    }
}
