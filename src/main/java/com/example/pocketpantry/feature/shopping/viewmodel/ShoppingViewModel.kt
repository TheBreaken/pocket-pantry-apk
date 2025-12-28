package com.example.pocketpantry.feature.shopping.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class ShoppingUiState (
    val items: List<String> = emptyList()
)

class ShoppingViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(ShoppingUiState(items = listOf("Bananas", "Milk")))
    val uiState: StateFlow<ShoppingUiState> = _uiState.asStateFlow()
}