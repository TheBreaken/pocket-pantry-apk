package com.example.pocketpantry.feature.pantry.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.pocketpantry.PocketPantryApplication
import com.example.pocketpantry.data.model.PantryItem
import com.example.pocketpantry.data.pantry.PantryRepository
import com.example.pocketpantry.feature.pantry.ui.PantryEditUiState
import java.time.LocalDate
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
class PantryEditViewModel(
    private val pantryRepository: PantryRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(PantryEditUiState())
    val uiState: StateFlow<PantryEditUiState> = _uiState.asStateFlow()

    private var loadJob: Job? = null

    fun load(id: Long?) {
        loadJob?.cancel()
        if (id == null) {
            _uiState.value = PantryEditUiState()
            return
        }

        loadJob = viewModelScope.launch {
            val item = pantryRepository.getItem(id)
            _uiState.value = if (item != null) {
                PantryEditUiState(
                    id = item.id,
                    name = item.name,
                    quantityLabel = item.quantityLabel,
                    expiryDate = item.expiryDate,
                    canSave = true
                )
            } else {
                PantryEditUiState()
            }
        }
    }

    fun setName(name: String) {
        _uiState.update { state ->
            state.copy(
                name = name,
                canSave = canSave(name, state.quantityLabel)
            )
        }
    }

    fun setQuantityLabel(quantityLabel: String) {
        _uiState.update { state ->
            state.copy(
                quantityLabel = quantityLabel,
                canSave = canSave(state.name, quantityLabel)
            )
        }
    }

    fun setExpiryDate(expiryDate: LocalDate?) {
        _uiState.update { state ->
            state.copy(
                expiryDate = expiryDate,
                canSave = canSave(state.name, state.quantityLabel)
            )
        }
    }

    fun save() {
        val currentState = _uiState.value
        if (!currentState.canSave) return

        viewModelScope.launch {
            pantryRepository.save(
                PantryItem(
                    id = currentState.id,
                    name = currentState.name.trim(),
                    quantityLabel = currentState.quantityLabel.trim(),
                    expiryDate = currentState.expiryDate
                )
            )
        }
    }

    private fun canSave(name: String, quantityLabel: String): Boolean {
        return name.isNotBlank() && quantityLabel.isNotBlank()
    }

    companion object {
        fun provideFactory(
            application: PocketPantryApplication
        ): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                PantryEditViewModel(application.appContainer.pantryRepository)
            }
        }
    }
}
