package com.example.pocketpantry.feature.pantry.ui

import java.time.LocalDate

enum class PantryFilter { All, ExpiringSoon, Expiring, }

data class PantryItemUi(
    val id: Long?,
    val name: String,
    val quantityLabel: String,
    val expiryDate: LocalDate? = null,
    val isExpired: Boolean = false,
    val isExpiringSoon: Boolean = false,
)

data class PantryUiState(
    val isLoading: Boolean = false,
    val query: String = "",
    val filter: PantryFilter = PantryFilter.All,
    val items: List<PantryItemUi> = emptyList(),
    val errorMessage: String? = null
)

data class PantryEditUiState(
    val name: String = "",
    val quantityLabel: String = "",
    val canSave: Boolean = false,
)