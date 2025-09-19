package com.negk.lerna.ui.game.memory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.negk.lerna.data.GameRepository

/**
 * Factory para crear instancias de [MemoryMatrixViewModel].
 * Permite inyectar dependencias como [GameRepository] en el ViewModel,
 * lo que facilita las pruebas y el desacoplamiento.
 */
class MemoryMatrixViewModelFactory(private val gameRepository: GameRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MemoryMatrixViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MemoryMatrixViewModel(gameRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}