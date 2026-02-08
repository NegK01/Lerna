@file:OptIn(ExperimentalMaterial3Api::class)

package com.negk.lernaminigames.feature.profile

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ProfileScreen() {
    Scaffold(topBar = { TopAppBar(title = { Text("Perfil / Stats") }) }) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Aquí van las estadisticas globales.")
            Spacer(Modifier.height(8.dp))
            Text("Quiza logros tambien.")
        }
    }
}
