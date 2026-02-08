@file:OptIn(ExperimentalMaterial3Api::class)

package com.negk.lernaminigames.feature.home

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.negk.lernaminigames.core.navigation.GameId

@Composable
fun HomeScreen(onGameSelected: (String) -> Unit) {
    Scaffold(topBar = { TopAppBar(title = { Text("Lerna") }) }) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            GameCard(
                title = "Tap rápido",
                subtitle = "Reacciona y suma puntos",
                onClick = { onGameSelected(GameId.Tap.id) }
            )
            GameCard(
                title = "Memoria (pronto)",
                subtitle = "Encuentra pares",
                onClick = { onGameSelected(GameId.Memory.id) }
            )
        }
    }
}

@Composable
private fun GameCard(title: String, subtitle: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        Column(Modifier.padding(16.dp)) {
            Text(title, style = MaterialTheme.typography.titleLarge)
            Spacer(Modifier.height(4.dp))
            Text(subtitle, style = MaterialTheme.typography.bodyMedium)
        }
    }
}
