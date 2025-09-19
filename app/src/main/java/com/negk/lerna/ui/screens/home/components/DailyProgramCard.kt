package com.negk.lerna.ui.screens.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.negk.lerna.R
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.negk.lerna.ui.components.BaseCard
import androidx.compose.ui.platform.LocalContext
import com.negk.lerna.data.GameRepository
import com.negk.lerna.data.gameDrawableMap

/**
 * DailyProgramCard
 * Tarjeta grande destacada para el programa del día en HomeScreen.
 * Usa BaseHomeCard y permite parametrizar altura, icono, título, descripción y texto del botón.
 *
 * @param modifier Modificador externo
 * @param height Altura de la tarjeta (por defecto 200.dp)
 * @param iconResource Recurso del icono (por defecto cerebro)
 * @param title Título de la tarjeta
 * @param description Descripción breve
 * @param imageResource Recurso de la imagen (por defecto memory_matrix)
 * @param imageSize Tamaño de la imagen (por defecto 80.dp)
 * @param buttonText Texto del botón de acción
 * @param onButtonClick Acción al pulsar el botón
 */
@Composable
fun DailyProgramCard(
	modifier: Modifier = Modifier,
	height: Dp = 200.dp,
	gameId: String? = null,
	imageSize: Dp = 80.dp,
	buttonText: String = "Jugar",
	onButtonClick: () -> Unit = {}
) {
	val context = LocalContext.current
	// 1. Obtener datos del juego si se pasa gameId
	val game = remember(gameId) {
		gameId?.let { GameRepository.getGameById(context, it) }
	}

	BaseCard(
	    modifier = modifier
	        .height(height)
	        .clickable(onClick = onButtonClick),
	    backgroundColor = MaterialTheme.colorScheme.surface,
	    elevation = 8.dp
	) {
		Column(
			modifier = Modifier.fillMaxSize(),
			verticalArrangement = Arrangement.SpaceBetween
		) {
			Row(
				modifier = Modifier.weight(1f).fillMaxWidth(),
				verticalAlignment = Alignment.CenterVertically,
			) {
				Column(
					modifier = Modifier.weight(1f),
					verticalArrangement = Arrangement.Center
				) {
					// 2. Mostrar título y descripción desde game o fallback
					Text(
						text = game?.title ?: "Titulo",
						style = MaterialTheme.typography.titleLarge,
						color = MaterialTheme.colorScheme.onSurface
					)
					Spacer(modifier = Modifier.height(8.dp))
					Text(
						text = game?.description ?: "Descripcion",
						style = MaterialTheme.typography.bodyMedium,
						color = MaterialTheme.colorScheme.onSurfaceVariant,
						textAlign = TextAlign.Start
					)
				}
				// 3. Imagen desde gameId
				val imageRes = game?.id?.let { id ->
					gameDrawableMap[id]
				} ?: R.drawable.memory_matrix // Recurso por defecto si no se encuentra
				Image(
					painter = painterResource(id = imageRes),
					contentDescription = game?.title ?: "Juego",
					modifier = Modifier
						.size(imageSize)
						.weight(1f)
						.padding(start = 20.dp, top = 8.dp)
				)
			}
		}
	}
}
