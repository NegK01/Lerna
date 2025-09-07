package com.negk.lerna.ui.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import com.negk.lerna.ui.screens.home.components.DailyProgramCard
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.negk.lerna.R
import androidx.navigation.compose.rememberNavController
import com.negk.lerna.ui.screens.home.components.RecommendedGameCard

/**
 * HomeScreen
 * Pantalla principal que muestra el 'Programa del día'.
 * Aquí se pueden agregar secciones como juegos recomendados, test, etc.
 */
@Composable
fun HomeScreen(navController: NavController) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "Programa del día",
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(15.dp))

        // Tarjeta destacada del programa del día
        DailyProgramCard(
            height = 200.dp,
            title = "Memory Matrix",
            description = "Entrena tu memoria recordando patrones",
            imageResource = R.drawable.memory_matrix,
            imageSize = 100.dp,
            buttonText = "Jugar",
            onButtonClick = { navController.navigate("games") }
        )

        Spacer(modifier = Modifier.height(30.dp))

        Text(
            text = "Juegos recomendados",
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(15.dp))

        RecommendedGameCard(
            height = 100.dp,
            title = "Memory Matrix",
            description = "Entrena tu memoria recordando patrones",
            buttonText = "Jugar",
            onButtonClick = { /* Acción de prueba */ }
        )
    }
}
@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    MaterialTheme {
        HomeScreen(navController = rememberNavController())
    }
}