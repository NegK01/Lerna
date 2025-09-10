package com.negk.lerna.ui.screens.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.negk.lerna.R
import com.negk.lerna.ui.components.BaseCard
import com.negk.lerna.ui.screens.home.HomeScreen

@Composable
fun RecommendedGameCard(
    modifier: Modifier = Modifier,
    height: Dp = 200.dp,
    title: String = "Titulo",
    description: String = "Descripcion",
    imageResource: Int = R.drawable.memory_matrix,
    imageSize: Dp = 150.dp,
    onButtonClick: () -> Unit = {}
) {
    BaseCard(
        modifier = modifier
            .height(height)
            .clickable(onClick = onButtonClick),
        backgroundColor = MaterialTheme.colorScheme.surface
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
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = description,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Start
                    )
                }
                Image(
                    painter = painterResource(id = imageResource),
                    contentDescription = null,
                    modifier = Modifier
                        .size(imageSize)
                        .weight(1f)
                        .padding(start = 50.dp)
                )
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun RecommendedGameCardPreview() {
    RecommendedGameCard(
        title = "Juego recomendado",
        description = "Pon a prueba tu memoria con este divertido reto.",
        imageResource = R.drawable.memory_matrix,
        onButtonClick = { /* Acción de prueba */ }
    )
}
