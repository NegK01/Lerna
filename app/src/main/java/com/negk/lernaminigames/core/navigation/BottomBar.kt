package com.negk.lernaminigames.core.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

/**
 * Representa un boton del menú inferior
 *
 * route  = a qué ruta navega al tocarlo
 * label  = texto que se ve
 * icon   = composable que dibuja el ícono
 */
data class BottomItem(
    val route: String,
    val label: String,
    val icon: @Composable () -> Unit
)

@Composable
fun BottomBar(nav: NavController) {
    // Lista de botones que tendra el menu inferior
    val items = listOf(
        BottomItem(Route.Home.path, "Home") { Icon(Icons.Filled.Home, contentDescription = null) },
        BottomItem(Route.Profile.path, "Perfil") { Icon(Icons.Filled.Person, contentDescription = null) },
        BottomItem(Route.Profile.path, "Configuracion") { Icon(Icons.Filled.Settings, contentDescription = null) },
    )

    // Esto "observa" en que ruta se encuentra actualmente para marcar el ítem como seleccionado
    val current = nav.currentBackStackEntryAsState().value?.destination?.route

    // NavigationBar = contenedor de la barra inferior (Material 3)
    NavigationBar {
        // Creamos cada boton de la barra
        items.forEach { item ->
            // Si la ruta actual coincide con la ruta del item, esta seleccionado
            val selected = current == item.route
            NavigationBarItem(
                selected = selected,
                // Cuando se toca un tab, se navega a esa ruta
                onClick = {
                    nav.navigate(item.route) {
                        /**
                         * popUpTo(Home):
                         * - Evita que se acumulen pantallas infinitas en el stack al tocar tabs
                         * - "Vuelve" al punto base (Home) como raíz del BottomNav
                         *
                         * saveState = true:
                         * - Guarda el estado de cada tab (scroll, posición, etc.)
                         */
                        popUpTo(Route.Home.path) { saveState = true }
                        /**
                         * launchSingleTop = true:
                         * - Si ya estas en esa misma pantalla, no la crea de nuevo encima
                         */
                        launchSingleTop = true
                        /**
                         * restoreState = true:
                         * - Si ya se había estado en ese tab, restaura su estado guardado
                         */
                        restoreState = true
                    }
                },
                icon = item.icon,
                label = { Text(item.label) }
            )
        }
    }
}
