package com.negk.lerna.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

/**
 * TestsScreen
 * Muestra los tests de autoevaluación disponibles.
 */
@Composable
fun TestsScreen(navController: NavController) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "Tests de Autoevaluación",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.semantics { heading() }
        )

        Spacer(modifier = Modifier.height(16.dp))
    }
}
