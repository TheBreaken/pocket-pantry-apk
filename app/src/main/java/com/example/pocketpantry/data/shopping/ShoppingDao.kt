package com.example.pocketpantry.data.shopping

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface ShoppingDao {
    @Query("SELECT * FROM shopping_items ORDER BY name ASC")
    fun observeItems(): Flow<List<ShoppingEntity>>

    @Upsert
    suspend fun upsert(item: ShoppingEntity): Long

    @Query("UPDATE shopping_items SET isChecked = :checked WHERE id = :id")
    suspend fun updateChecked(id: Long, checked: Boolean)

    @Query("DELETE FROM shopping_items WHERE id = :id")
    suspend fun delete(id: Long)
}
