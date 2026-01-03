package com.example.pocketpantry.feature.shopping.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.pocketpantry.PocketPantryApplication
import com.example.pocketpantry.data.model.ShoppingItem
import com.example.pocketpantry.data.shopping.ShoppingRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class ShoppingItemUi(
    val id: Long?,
    val name: String,
    val quantityLabel: String,
    val isChecked: Boolean
)

data class ShoppingUiState(
    val isLoading: Boolean = false,
    val items: List<ShoppingItemUi> = emptyList()
)

class ShoppingViewModel(
    private val shoppingRepository: ShoppingRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(ShoppingUiState(isLoading = true))
    val uiState: StateFlow<ShoppingUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            shoppingRepository.items.collect { items ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        items = items.map { item -> item.toUi() }
                    )
                }
            }
        }
    }

    private fun ShoppingItem.toUi(): ShoppingItemUi = ShoppingItemUi(
        id = id,
        name = name,
        quantityLabel = quantityLabel,
        isChecked = isChecked
    )

    companion object {
        fun provideFactory(
            application: PocketPantryApplication
        ): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                ShoppingViewModel(application.appContainer.shoppingRepository)
            }
        }
    }
}
