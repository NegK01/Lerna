package com.negk.lerna.ui.screens.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.negk.lerna.R
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Surface
import com.negk.lerna.ui.components.BaseHomeCard

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
	title: String = "Titulo",
	description: String = "Descripcion",
	imageResource: Int = R.drawable.memory_matrix,
	imageSize: Dp = 80.dp,
	buttonText: String = "Jugar",
	onButtonClick: () -> Unit = {}
) {
	BaseHomeCard(
		modifier = modifier.height(height),
		backgroundColor = MaterialTheme.colorScheme.primaryContainer
	) {
		Column(
			modifier = Modifier.fillMaxSize(),
			verticalArrangement = Arrangement.SpaceBetween
		) {
			Row(
				modifier = Modifier.weight(1f).fillMaxWidth(),
				verticalAlignment = Alignment.CenterVertically,
			) {
				// Columna de texto a la izquierda
				Column(
					modifier = Modifier.weight(1f),
					verticalArrangement = Arrangement.Center
				) {
					Text(
						text = title,
						style = MaterialTheme.typography.titleLarge,
						color = MaterialTheme.colorScheme.onPrimaryContainer
					)
					Spacer(modifier = Modifier.height(8.dp))
					Text(
						text = description,
						style = MaterialTheme.typography.bodyMedium,
						color = MaterialTheme.colorScheme.onPrimaryContainer,
						textAlign = TextAlign.Start
					)
				}
				// Imagen a la derecha
				Image(
					painter = painterResource(id = imageResource),
					contentDescription = null,
					modifier = Modifier
						.size(imageSize)
						.weight(1f)
						.padding(start = 20.dp, top = 8.dp)
				)
			}
			Spacer(modifier = Modifier.height(12.dp))
			// Botón de acción abajo, ocupa todo el ancho
			val interactionSource = remember { MutableInteractionSource() }
			Button(
				onClick = onButtonClick,
				modifier = Modifier.fillMaxWidth(),
				interactionSource = interactionSource,
				shape = RoundedCornerShape(12.dp),
				colors = ButtonDefaults.buttonColors(
					containerColor = MaterialTheme.colorScheme.primary,
					contentColor = MaterialTheme.colorScheme.onSecondary
				)
			) {
				Text(buttonText, style = MaterialTheme.typography.titleMedium)
			}
		}
	}
}

@Preview(showBackground = true)
@Composable
fun DailyProgramCardPreview() {
	MaterialTheme {
		Surface(modifier = Modifier.padding(16.dp)) {
			DailyProgramCard(
				height = 220.dp,
				title = "Memory Matrix",
				description = "Entrena tu memoria recordando patrones",
				imageResource = R.drawable.memory_matrix,
				imageSize = 100.dp,
				buttonText = "Jugar",
				onButtonClick = { /* Acción de prueba */ }
			)
		}
	}
}
