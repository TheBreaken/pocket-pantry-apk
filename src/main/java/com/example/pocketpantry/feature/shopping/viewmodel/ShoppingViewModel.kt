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
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
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
    val uiState: StateFlow<ShoppingUiState> = shoppingRepository.items
        .map { items ->
            ShoppingUiState(
                isLoading = false,
                items = items.map { item -> item.toUi() }
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = ShoppingUiState(isLoading = true)
        )

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
