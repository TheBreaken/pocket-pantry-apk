package com.example.pocketpantry.feature.pantry.viewmodel

import androidx.lifecycle.ViewModel
import com.example.pocketpantry.feature.pantry.ui.PantryItemUi
import com.example.pocketpantry.feature.pantry.ui.PantryUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class PantryViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(
        PantryUiState(
            items = listOf(
                PantryItemUi(id = 1, name = "Rice", quantityLabel = "1", isExpiringSoon = true),
                PantryItemUi(id = 2, name = "Banana", quantityLabel = "2")
            ),
        )
    )

    val uiState: StateFlow<PantryUiState> = _uiState.asStateFlow()
}