package com.negk.lerna

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.negk.lerna.ui.game.memory.MemoryMatrixGame
import com.negk.lerna.ui.theme.LernaTheme
import com.negk.lerna.data.GameRegistry

class GameActivity : ComponentActivity() {

    companion object {
        private const val EXTRA_GAME_ID = "gameId"

        fun createIntent(context: Context, gameId: String): Intent {
            return Intent(context, GameActivity::class.java).apply {
                putExtra(EXTRA_GAME_ID, gameId)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val gameId = intent.getStringExtra(EXTRA_GAME_ID) ?: run {
            finish()
            return
        }

        val gameComposable = GameRegistry.registry[gameId] ?: run {
            finish()
            return
        }

        setContent {
            LernaTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                    ) {
                        gameComposable()
                    }
                }
            }
        }
    }
}
