package com.example.pocketpantry.feature.pantry.viewmodel

import androidx.lifecycle.ViewModel
import com.example.pocketpantry.feature.pantry.ui.PantryEditUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class PantryEditViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(PantryEditUiState())
    val uiState: StateFlow<PantryEditUiState> = _uiState.asStateFlow()

    fun load(id: Long?) {
        // @todo load from repository
        _uiState.value = PantryEditUiState(name = "Milk", quantityLabel = "1", canSave = true)
    }

    fun setName(name: String) {
        val s = _uiState.value.copy(name = name)
        _uiState.value = s.copy(canSave = name.isNotBlank())
    }

    fun setQuantityLabel(quantityLabel: String) {
        val s = _uiState.value.copy(quantityLabel = quantityLabel)
        _uiState.value = s.copy(canSave = quantityLabel.isNotBlank())
    }

    fun save() {
        // @todo persist
    }
}