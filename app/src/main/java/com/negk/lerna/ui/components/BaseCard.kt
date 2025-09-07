package com.negk.lerna.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * BaseHomeCard
 * Componente base reutilizable para tarjetas de la HomeScreen.
 * Permite definir esquinas redondeadas, fondo, sombra y padding.
 *
 * @param modifier Modificador externo
 * @param cornerRadius Radio de las esquinas
 * @param backgroundColor Color de fondo (por defecto surfaceVariant)
 * @param elevation Elevación/sombra
 * @param content Contenido de la tarjeta
 */
@Composable
fun BaseCard(
	modifier: Modifier = Modifier,
	cornerRadius: Dp = 24.dp,
	backgroundColor: Color = MaterialTheme.colorScheme.surfaceVariant,
	elevation: Dp = 4.dp,
	content: @Composable () -> Unit
) {
	Surface(
		modifier = modifier
			.shadow(elevation, RoundedCornerShape(cornerRadius)),
		shape = RoundedCornerShape(cornerRadius),
		color = backgroundColor,
		tonalElevation = elevation
	) {
		Box(modifier = Modifier.padding(16.dp)) {
			content()
		}
	}
}
