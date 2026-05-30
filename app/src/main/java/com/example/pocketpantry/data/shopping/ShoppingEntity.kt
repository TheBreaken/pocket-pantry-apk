package com.example.pocketpantry.data.shopping

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.pocketpantry.data.model.ShoppingItem

@Entity(tableName = "shopping_items")
data class ShoppingEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val quantityLabel: String,
    val isChecked: Boolean
)

fun ShoppingEntity.toDomain(): ShoppingItem = ShoppingItem(
    id = id,
    name = name,
    quantityLabel = quantityLabel,
    isChecked = isChecked
)

fun ShoppingItem.toEntity(): ShoppingEntity = ShoppingEntity(
    id = id ?: 0,
    name = name,
    quantityLabel = quantityLabel,
    isChecked = isChecked
)
