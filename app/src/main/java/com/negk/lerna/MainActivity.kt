package com.negk.lerna
/**
 * MainActivity
 * Actividad principal de la app Lerna – Brain Training.
 * Gestiona la navegación entre las pantallas principales usando Jetpack Compose y Navigation Compose.
 * Pantallas: Home, Juegos, Tests, Perfil.
 */
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.negk.lerna.navigation.Screen
import com.negk.lerna.ui.screens.home.HomeScreen
import com.negk.lerna.ui.screens.GamesScreen
import com.negk.lerna.ui.screens.TestsScreen
import com.negk.lerna.ui.screens.ProfileScreen
import com.negk.lerna.ui.theme.LernaTheme
import com.negk.lerna.ui.components.Header

class MainActivity : ComponentActivity() {
    /**
     * Método de ciclo de vida onCreate.
     * Configura el tema, la navegación y la estructura principal de la app.
     * Integra el componente Header como topBar del Scaffold.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LernaTheme {
                val navController = rememberNavController()
                val items = Screen.all // Lista de pantallas para la barra de navegación, este es un objeto creado en navigation/screen.kt
                Scaffold(
                    topBar = {
                        // Encabezado fijo
                        Header()
                    },
                    bottomBar = {
                        Column {
                            HorizontalDivider(
                                color = MaterialTheme.colorScheme.outline.copy(alpha = 0.9f),
                                thickness = 1.dp
                            )
                            NavigationBar(
                                containerColor = MaterialTheme.colorScheme.background,
                                contentColor = MaterialTheme.colorScheme.onSurface
                            ) {
                                val navBackStackEntry by navController.currentBackStackEntryAsState()
                                val currentRoute = navBackStackEntry?.destination?.route
                                items.forEach { screen ->
                                    val (icon, label) = when (screen) {
                                        Screen.Home -> Icons.Default.Home to "Home"
                                        Screen.Games -> Icons.Default.Home to "Juegos"
                                        Screen.Tests -> Icons.Default.Person to "Tests"
                                        Screen.Profile -> Icons.Default.Person to "Perfil"
                                    }
                                    NavigationBarItem(
                                        selected = currentRoute == screen.route,
                                        onClick = {
                                            navController.navigate(screen.route) {
                                                popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                                                launchSingleTop = true
                                                restoreState = true
                                            }
                                        },
                                        icon = { Icon(icon, contentDescription = label) },
                                        label = { Text(label) },
                                        alwaysShowLabel = true,
                                        colors = androidx.compose.material3.NavigationBarItemDefaults.colors(
                                            selectedIconColor = MaterialTheme.colorScheme.onPrimary,
                                            unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                            selectedTextColor = MaterialTheme.colorScheme.onPrimary,
                                            unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                            indicatorColor = MaterialTheme.colorScheme.surfaceVariant // el "pill" de selección
                                        )
                                    )
                                }
                            }
                        }
                    }
                ) { inner ->

                    // Host de navegación: define las rutas y composables de cada pantalla
                    NavHost(
                        navController = navController,
                        startDestination = Screen.Home.route,
                        modifier = Modifier.padding(inner)
                    ) {
                        composable(Screen.Home.route) { HomeScreen(navController) }
                        composable(Screen.Games.route) { GamesScreen(navController) }
                        composable(Screen.Tests.route) { TestsScreen(navController) }
                        composable(Screen.Profile.route) { ProfileScreen(navController) }
                    }
                }
            }
        }
    }
}
