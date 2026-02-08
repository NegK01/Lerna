package com.negk.lernaminigames.core.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.negk.lernaminigames.feature.game.GameScreen        // <-- Pantalla del juego (otro archivo)
import com.negk.lernaminigames.feature.home.HomeScreen        // <-- Pantalla Home (otro archivo)
import com.negk.lernaminigames.feature.pregame.PreGameScreen  // <-- Pantalla Pre-juego (otro archivo)
import com.negk.lernaminigames.feature.profile.ProfileScreen  // <-- Pantalla Perfil (otro archivo)

/**
 * AppNavHost:
 * - Crea el NavController (el control de navegación)
 * - Monta el Scaffold principal (layout general con el bottom bar [la barra inferior])
 */
@Composable
fun AppNavHost() { // NavegacionPrincipal
    // rememberNavController() crea y recuerda el controlador de navegación
    // Se "recuerda" entre recomposiciones, para no volver a crear las pantallas a cada rato
    val nav = rememberNavController()

    // Llamamos a AppScaffold (función privada de este mismo archivo)
    // y le pasamos el NavController para que maneje rutas + bottom bar
    AppScaffold(nav)
}

/**
 * AppScaffold:
 * - Es el "marco" de la app
 * - Decide cuando mostrar el menú inferior (BottomBar)
 * - Define el grafo de navegación (NavHost + composable routes)
 */
@Composable
private fun AppScaffold(nav: NavHostController) {
    // Observa la pantalla actual (entrada actual en el backstack)
    // Esto hace que Compose se actualice cuando se cambia de pantalla
    val entradaBackStack by nav.currentBackStackEntryAsState()
    val rutaActual = entradaBackStack?.destination?.route // Obtiene la ruta actual como string (ej: "home", "profile", "pregame/tap")

    // Solo mostramos la barra inferior en pantallas "raíz" (Home y Perfil), en PreGame y Game la ocultamos
    val mostrarBarraInferior = rutaActual == Route.Home.path || rutaActual == Route.Profile.path

    // Scaffold = layout base de Material
    // Aqui vive el bottomBar y el contenido central
    Scaffold(
        bottomBar = {
            // BottomBar(nav) viene de otro archivo (BottomBar.kt)
            // Se usa para mostrar la navegacion inferior con la estructura que se creo en ese otro archivo
            if (mostrarBarraInferior) BottomBar(nav)
        }
    ) { padding ->
        // NavHost = el contenedor que decide qué pantalla mostrar segun la ruta actual
        NavHost(
            navController = nav, // nav, el parametro de controlador de navegacion que habiamos enviado desde en fun AppNavHost() en este mismo archivo
            startDestination = Route.Home.path, // Ruta inicial: Home
            modifier = Modifier.padding(padding) // Usa el padding del Scaffold para no tapar contenido con la barra
        ) {
            // ---------- HOME ----------
            composable(Route.Home.path) {
                // HomeScreen viene de OTRO archivo (feature/home/HomeScreen.kt)
                HomeScreen(
                    // Callback que se dispara cuando seleccionas un juego en Home, justamente esta funcion desde
                    // su archivo de origen esta a la espera de estos datos como parametro
                    // gameId es un string como "tap" o "memory" que indica precisamente cual juego cargar
                    onGameSelected = { gameId ->

                        // Navega a PreGame con el id del juego
                        // Route.PreGame.create(idJuego) viene de otro archivo (Routes.kt) el cual nos permite crear el string de las rutas
                        nav.navigate(Route.PreGame.create(gameId))
                    }
                )
            }

            // ---------- PERFIL ----------
            composable(Route.Profile.path) {
                // ProfileScreen viene de otro archivo (feature/profile/ProfileScreen.kt)
                ProfileScreen()
            }

            // ---------- PRE-GAME ----------
            composable(
                route = Route.PreGame.path, // "pregame/{gameId}" viene de otro archivo (Routes.kt)
                // Declaramos el argumento "gameId" como String
                arguments = listOf(navArgument("gameId") { type = NavType.StringType })
            ) { backStack ->
                // Tomamos el argumento "gameId" de la ruta
                // Si el argumento llega null, usamos "tap" por defecto
                val gameId = backStack.arguments?.getString("gameId") ?: "tap"

                // PreGameScreen viene de otro archivo (feature/pregame/PreGameScreen.kt)
                PreGameScreen(
                    gameId = gameId,
                    // Al presionar "Jugar" navegamos a la pantalla de Game, y creamos la ruta con el mismo gameId con el que entramos al PreGame
                    onPlay = { nav.navigate(Route.Game.create(gameId)) },
                    onBack = { nav.popBackStack() } // popBackStack() = volver a la pantalla anterior del stack
                )
            }

            // ---------- GAME ----------
            composable(
                route = Route.Game.path, // "game/{gameId}" viene de Routes.kt
                arguments = listOf(navArgument("gameId") { type = NavType.StringType })
            ) { backStack ->
                // Tomamos el argumento "gameId" de la ruta
                // Si el argumento llega null, usamos "tap" por defecto
                val gameId = backStack.arguments?.getString("gameId") ?: "tap"
                // GameScreen viene de otro archivo (feature/game/GameScreen.kt)
                GameScreen(
                    gameId = gameId,
                    // Volver a la pantalla anterior (PreGame)
                    onExitToPreGame = { nav.popBackStack() }
                )
            }
        }
    }
}
