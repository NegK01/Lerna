package com.negk.lerna.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import com.negk.lerna.ui.screens.home.components.DailyProgramCard
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.negk.lerna.R
import com.negk.lerna.ui.screens.home.components.RecommendedGameCard
import android.content.Intent
import androidx.compose.ui.platform.LocalContext
import com.negk.lerna.PreGameActivity

@Composable
fun HomeScreen(navController: NavController) {
    val context = LocalContext.current // 1. Obtener contexto

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {

        Spacer(modifier = Modifier.height(15.dp))

        // Tarjeta destacada del programa del día
        DailyProgramCard(
            height = 120.dp,
            gameId = "memory_matrix", // ID del juego destacado
            imageSize = 100.dp,
            buttonText = "Jugar",
            onButtonClick = {
                // 2. Crear Intent y pasar gameId
                val intent = Intent(context, PreGameActivity::class.java)
                intent.putExtra("gameId", "memory_matrix") // Aquí el id real del juego
                context.startActivity(intent) // 3. Abrir PreGameActivity
            }
        )

        Spacer(modifier = Modifier.height(30.dp))

        Text(
            text = "Juegos recomendados",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onBackground,
        )

        Spacer(modifier = Modifier.height(15.dp))

        // Ejemplo de juego recomendado
        RecommendedGameCard(
            height = 100.dp,
            gameId = "memory_matrix",
            onButtonClick = {
                val intent = Intent(context, PreGameActivity::class.java)
                intent.putExtra("gameId", "memory_matrix") // id del juego
                context.startActivity(intent)
            }
        )

        Spacer(modifier = Modifier.height(15.dp))

        RecommendedGameCard(
            height = 100.dp,
            gameId = "speed_match",
            onButtonClick = {
                val intent = Intent(context, PreGameActivity::class.java)
                intent.putExtra("gameId", "speed_match")
                context.startActivity(intent)
            }
        )
    }
}
