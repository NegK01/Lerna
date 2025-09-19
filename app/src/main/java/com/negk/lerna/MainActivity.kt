package com.negk.lerna
/**
 * MainActivity
 * Actividad principal de la app Lerna – Brain Training.
 * Gestiona la navegación entre las pantallas principales usando Jetpack Compose y Navigation Compose.
 * Pantallas: Home, Juegos, Tests, Perfil.
 */
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.negk.lerna.navigation.Screen
import com.negk.lerna.data.Graph
import com.negk.lerna.ui.screens.home.HomeScreen
import com.negk.lerna.ui.screens.GamesScreen
import com.negk.lerna.ui.screens.TestsScreen
import com.negk.lerna.ui.screens.ProfileScreen
import com.negk.lerna.ui.theme.LernaTheme
import com.negk.lerna.ui.components.Header
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    companion object {
        private const val TAG = "MainActivity"
        private const val LERNA_APP_PREFS = "LernaApp"
        private const val LAST_SYNC_VERSION_CODE_KEY = "last_sync_version_code"
    }

    /**
     * Método de ciclo de vida onCreate.
     * Configura el tema, la navegación y la estructura principal de la app.
     * Integra el componente Header como topBar del Scaffold.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        Graph.provide(this)

        checkForDataSync()

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
                        // TODO Verificar si todos usan NavController sino lo eliminamos
                        composable(Screen.Home.route) { HomeScreen(navController) }
                        composable(Screen.Games.route) { GamesScreen(navController) }
                        composable(Screen.Tests.route) { TestsScreen(navController) }
                        composable(Screen.Profile.route) { ProfileScreen(navController) }
                    }
                }
            }
        }
    }

    /**
     * Comprueba si la app ha sido actualizada y sincroniza los datos de los juegos si es necesario.
     */
    private fun checkForDataSync() {
        val prefs = getSharedPreferences(LERNA_APP_PREFS, Context.MODE_PRIVATE)
        // Se maneja la posible ClassCastException leyendo el valor como un tipo Number y convirtiéndolo a Long.
        // Esto asegura la retrocompatibilidad si el valor fue guardado previamente como un Int.
        val lastSyncValue = prefs.all[LAST_SYNC_VERSION_CODE_KEY]
        val lastSyncVersionCode = (lastSyncValue as? Number)?.toLong() ?: 0L

        try {
            val packageInfo = packageManager.getPackageInfo(packageName, 0)
            val currentVersionCode = packageInfo.longVersionCode

            if (currentVersionCode > lastSyncVersionCode) {
                lifecycleScope.launch {
                    // Sincronizar datos en un hilo de fondo
                    Graph.gameRepository.syncGamesFromJson(applicationContext)

                    // Actualizar la versión guardada tras una sincronización exitosa
                    prefs.edit().putLong(LAST_SYNC_VERSION_CODE_KEY, currentVersionCode).apply()
                }
            }
        } catch (e: android.content.pm.PackageManager.NameNotFoundException) {
            // Registrar el error si no se puede obtener la información del paquete.
            Log.e(TAG, "Failed to get package info for data sync.", e)
        }
    }
}
