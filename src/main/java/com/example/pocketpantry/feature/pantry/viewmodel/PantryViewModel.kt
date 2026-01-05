package com.example.pocketpantry.feature.pantry.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.pocketpantry.PocketPantryApplication
import com.example.pocketpantry.data.model.PantryItem
import com.example.pocketpantry.data.pantry.PantryRepository
import com.example.pocketpantry.feature.pantry.ui.PantryItemUi
import com.example.pocketpantry.feature.pantry.ui.PantryUiState
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import java.time.LocalDate
import kotlin.time.Duration.Companion.seconds

class PantryViewModel(
    private val pantryRepository: PantryRepository
) : ViewModel() {
    val uiState: StateFlow<PantryUiState> = pantryRepository.items
        .map { items ->
            val today = LocalDate.now()
            PantryUiState(
                isLoading = false,
                items = items.map { item -> item.toUi(today) }
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5.seconds),
            initialValue = PantryUiState(isLoading = true)
        )

    private fun PantryItem.toUi(referenceDate: LocalDate): PantryItemUi {
        val isExpired = expiryDate?.isBefore(referenceDate) == true
        val isExpiringSoon = expiryDate?.let { !isExpired && !it.isAfter(referenceDate.plusDays(3)) } == true
        return PantryItemUi(
            id = id,
            name = name,
            quantityLabel = quantityLabel,
            expiryDate = expiryDate,
            isExpired = isExpired,
            isExpiringSoon = isExpiringSoon
        )
    }

    companion object {
        fun provideFactory(
            application: PocketPantryApplication
        ): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                PantryViewModel(application.appContainer.pantryRepository)
            }
        }
    }

    fun deleteItem(id: Long) {
        viewModelScope.launch {
            pantryRepository.delete(id)
        }
    }
}
