package com.negk.lerna

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.negk.lerna.data.GameRepository
import com.negk.lerna.ui.screens.pre_game.PreGameScreen
import com.negk.lerna.ui.theme.LernaTheme

class PreGameActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Obtener gameId desde el Intent
        val gameId = intent.getStringExtra("gameId") ?: ""

        // Obtener datos del juego usando GameRepository
        val gameData = GameRepository.getGameById(this, gameId)

        setContent {
            LernaTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    PreGameScreen(
                        game = gameData,
                        modifier = Modifier.padding(innerPadding),
                        onPlayClick = {
                            // Crear Intent para abrir GameActivity
                            gameData.id.takeIf { it.isNotEmpty() }?.let { id ->
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
