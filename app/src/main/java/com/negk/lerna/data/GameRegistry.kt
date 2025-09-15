package com.negk.lerna.data

import androidx.compose.runtime.Composable
import com.negk.lerna.ui.game.memory.MemoryMatrixGame
//import com.negk.lerna.ui.game.speed.SpeedMatchGame

object GameRegistry {
    val registry: Map<String, @Composable () -> Unit> = mapOf(
        "memory_matrix" to { MemoryMatrixGame() },
//        "speed_match" to { SpeedMatchGame() }
    )
}
