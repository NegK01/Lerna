package com.negk.lerna

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.produceState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.negk.lerna.data.model.Game
import com.negk.lerna.data.Graph
import com.negk.lerna.ui.screens.pre_game.PreGameScreen
import com.negk.lerna.ui.theme.LernaTheme

class PreGameActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Obtener gameId desde el Intent
        val gameId = intent.getStringExtra("gameId") ?: ""

        setContent {
            LernaTheme {
                // Obtener datos del juego de forma reactiva
                val gameData by produceState<Game?>(initialValue = null, gameId) {
                    if (gameId.isNotEmpty()) {
                        Graph.gameRepository.getGameById(gameId).collect { value = it }
                    }
                }

                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    if (gameData == null) {
                        // Estado de carga a pantalla completa
                        Box(
                            modifier = Modifier.fillMaxSize().padding(innerPadding),
                            contentAlignment = Alignment.Center
                        ) {
                            // Si llega un gameid nulo se mostrara la animacion infinitamente, da igual pues se procura que eso no pase, ademas no se va poder accerder pues esa card no se mostrara
                            CircularProgressIndicator()
                        }
                    } else {
                        PreGameScreen(
                            game = gameData!!,
                            modifier = Modifier.padding(innerPadding),
                            onPlayClick = {
                                // Crear Intent para abrir GameActivity
                                gameData!!.id.takeIf { it.isNotEmpty() }?.let { id ->
                                    val intent = Intent(this, GameActivity::class.java)
                                    intent.putExtra("gameId", id)
                                    startActivity(intent)
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}
