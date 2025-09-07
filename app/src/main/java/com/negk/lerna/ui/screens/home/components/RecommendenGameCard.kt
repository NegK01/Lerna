package com.negk.lerna.ui.screens.home.components

import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.ui.tooling.preview.Preview
import com.negk.lerna.ui.components.BaseHomeCard

@Composable
fun RecommendedGameCard(
    modifier: Modifier = Modifier,
    height: Dp = 200.dp,
    title: String = "Titulo",
    description: String = "Descripcion",
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
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimaryContainer,
                    modifier = Modifier
                        .size(48.dp)
                        .padding(start = 20.dp, top = 8.dp)
                )
                /*Image(
                    painter = painterResource(id = imageResource),
                    contentDescription = null,
                    modifier = Modifier
                        .size(imageSize)
                        .weight(1f)
                        .padding(start = 20.dp, top = 8.dp)
                )*/
            }
            Spacer(modifier = Modifier.height(12.dp))
            // Botón de acción abajo, ocupa todo el ancho
//            val interactionSource = remember { MutableInteractionSource() }
//            Button(
//                onClick = onButtonClick,
//                modifier = Modifier.fillMaxWidth(),
//                interactionSource = interactionSource,
//                shape = RoundedCornerShape(12.dp),
//                colors = ButtonDefaults.buttonColors(
//                    containerColor = MaterialTheme.colorScheme.primary,
//                    contentColor = MaterialTheme.colorScheme.onSecondary
//                )
//            ) {
//                Text(buttonText, style = MaterialTheme.typography.titleMedium)
//            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RecommendedGameCardPreview() {
    MaterialTheme {
        Surface(modifier = Modifier.padding(16.dp)) {
            RecommendedGameCard(
                height = 100.dp,
                title = "Memory Matrix",
                description = "Entrena tu memoria recordando patrones",
                buttonText = "Jugar",
                onButtonClick = { /* Acción de prueba */ }
            )
        }
    }
}
