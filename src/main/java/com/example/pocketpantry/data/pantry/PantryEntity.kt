package com.example.pocketpantry.data.pantry

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.pocketpantry.data.model.PantryItem
import java.time.LocalDate

@Entity(tableName = "pantry_items")
data class PantryEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val quantityLabel: String,
    val expiryDate: LocalDate?
)

fun PantryEntity.toDomain(): PantryItem = PantryItem(
    id = id,
    name = name,
    quantityLabel = quantityLabel,
    expiryDate = expiryDate
)

fun PantryItem.toEntity(): PantryEntity = PantryEntity(
    id = id ?: 0,
    name = name,
    quantityLabel = quantityLabel,
    expiryDate = expiryDate
)
