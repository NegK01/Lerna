package com.negk.lerna.ui.game.memory

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlin.random.Random
import androidx.core.content.edit

/**
 * ViewModel para el juego Memory Matrix.
 * Maneja el estado del juego, incluyendo secuencia lineal, progreso y lógica de juego.
 */
class MemoryMatrixViewModel(application: Application) : AndroidViewModel(application) {
    companion object {
        private const val KEY_SEQUENCE = "sequence"
        private const val CELL_LIT_DURATION_MS = 1000L
        private const val PAUSE_BETWEEN_CELLS_MS = 500L
        const val WIN_SEQUENCE_COUNT = 100
    }

    private val prefs = application.getSharedPreferences("MemoryMatrix", Context.MODE_PRIVATE)

    // Tamaño de la rejilla (fijo en 3x3 para simplicidad)
    private val _gridSize = MutableStateFlow(3)
    val gridSize: StateFlow<Int> = _gridSize

    // Secuencia actual a recordar (persistida)
    private val _sequence = MutableStateFlow<List<Int>>(loadSequence())
    val sequence: StateFlow<List<Int>> = _sequence

    // Indica si se está mostrando la secuencia actualmente
    private val _isShowingSequence = MutableStateFlow(false)
    val isShowingSequence: StateFlow<Boolean> = _isShowingSequence

    // Celda actualmente iluminada durante la secuencia
    private val _currentLitCell = MutableStateFlow<Int?>(null)
    val currentLitCell: StateFlow<Int?> = _currentLitCell

    // Secuencia de entrada del jugador
    private val _playerSequence = MutableStateFlow<List<Int>>(emptyList())

    // Contador de secuencias completadas
    private val _sequenceCount = MutableStateFlow(if (_sequence.value.isNotEmpty()) _sequence.value.size - 1 else 0)
    val sequenceCount: StateFlow<Int> = _sequenceCount

    // Estado de fin del juego
    private val _gameOver = MutableStateFlow(false)
    val gameOver: StateFlow<Boolean> = _gameOver

    // Juego completado (100 secuencias)
    private val _gameCompleted = MutableStateFlow(false)
    val gameCompleted: StateFlow<Boolean> = _gameCompleted

    // Mostrar "Achieved" después de completar secuencia
    private val _showAchieved = MutableStateFlow(false)
    val showAchieved: StateFlow<Boolean> = _showAchieved

    private fun loadSequence(): List<Int> {
        val sequenceString = prefs.getString(KEY_SEQUENCE, "") ?: ""
        return if (sequenceString.isNotEmpty()) {
            sequenceString.split(",").mapNotNull { it.toIntOrNull() }
        } else {
            emptyList()
        }
    }

    private fun saveSequence(sequence: List<Int>) {
        val sequenceString = sequence.joinToString(",")
        prefs.edit { putString(KEY_SEQUENCE, sequenceString) }
    }

    /**
     * Inicia la secuencia actual, mostrando una celda a la vez con animación.
     */
    fun startSequence() {
        // Si es el inicio o se continúa tras acertar, se añade una nueva celda a la secuencia.
        if (_sequence.value.isEmpty() || _showAchieved.value) {
            val totalCells = _gridSize.value * _gridSize.value
            val newCell = Random.nextInt(totalCells)
            _sequence.value = _sequence.value + newCell
            saveSequence(_sequence.value)
        }

        _playerSequence.value = emptyList()
        _currentLitCell.value = null
        _isShowingSequence.value = true
        _gameOver.value = false
        _showAchieved.value = false // Ocultar "¡Logrado!" para iniciar la nueva secuencia

        // Animar mostrando la secuencia una celda a la vez
        viewModelScope.launch {
            for (cell in _sequence.value) {
                _currentLitCell.value = cell
                delay(CELL_LIT_DURATION_MS) // Mostrar celda
                _currentLitCell.value = null
                delay(PAUSE_BETWEEN_CELLS_MS) // Pausa entre celdas
            }
            _isShowingSequence.value = false
        }
    }

    /**
     * Maneja el clic en una celda de la rejilla.
     */
    fun onCellClick(index: Int) {
        if (_isShowingSequence.value || _gameOver.value || _gameCompleted.value || _sequence.value.isEmpty() || _showAchieved.value) return // Ignorar clics si no es el turno del jugador

        _playerSequence.value += index
        val currentIndex = _playerSequence.value.size - 1

        // Verificar si es correcto
        if (index != _sequence.value[currentIndex]) {
            _gameOver.value = true
            return
        }

        // Si completa la secuencia
        if (_playerSequence.value == _sequence.value) {
            _sequenceCount.value = _sequence.value.size // Actualizar contador de secuencias completadas
            if (_sequenceCount.value >= WIN_SEQUENCE_COUNT) {
                _gameCompleted.value = true
            } else {
                // La secuencia es correcta, mostrar "¡Logrado!" y esperar a que el usuario presione "Iniciar Secuencia" para continuar.
                _showAchieved.value = true
            }
        }
    }

    /**
     * Reinicia el juego completamente.
     */
    fun resetGame() {
        _sequence.value = emptyList()
        saveSequence(emptyList())
        _playerSequence.value = emptyList()
        _sequenceCount.value = 0
        _gameOver.value = false
        _gameCompleted.value = false
        _showAchieved.value = false
        _currentLitCell.value = null
        _isShowingSequence.value = false
    }
}
