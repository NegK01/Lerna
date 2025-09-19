package com.negk.lerna.ui.game.utils

import androidx.compose.runtime.Composable
import com.negk.lerna.ui.game.memory.MemoryMatrixGame
//import com.negk.lerna.ui.game.speed.SpeedMatchGame

object GameRegistry {
    val registry: Map<String, @Composable (onExit: () -> Unit) -> Unit> = mapOf(
        "memory_matrix" to { onExit -> MemoryMatrixGame(onExit = onExit) },
//        "speed_match" to { SpeedMatchGame() }
    )
}
