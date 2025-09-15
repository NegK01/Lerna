package com.negk.lerna.ui.game.memory

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MemoryMatrixViewModel : ViewModel() {
    private val _score = MutableStateFlow(0)
    val score: StateFlow<Int> = _score

    private val _levelComplete = MutableStateFlow(false)
    val levelComplete: StateFlow<Boolean> = _levelComplete

    fun incrementScore() {
        _score.value++
        if (_score.value >= 5) { // ejemplo, completar nivel a 5 puntos
            _levelComplete.value = true
        }
    }

    fun dismissLevelComplete() {
        _levelComplete.value = false
    }
}
