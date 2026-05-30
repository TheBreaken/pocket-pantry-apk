package com.example.pocketpantry.data.model

import java.time.LocalDate

data class PantryItem(
    val id: Long?,
    val name: String,
    val quantityLabel: String,
    val expiryDate: LocalDate? = null
)
