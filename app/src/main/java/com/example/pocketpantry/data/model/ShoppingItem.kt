package com.example.pocketpantry.data.model

data class ShoppingItem(
    val id: Long?,
    val name: String,
    val quantityLabel: String,
    val isChecked: Boolean = false
)
