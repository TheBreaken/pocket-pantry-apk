package com.example.pocketpantry.data.pantry

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface PantryDao {
    @Query("SELECT * FROM pantry_items ORDER BY name ASC")
    fun observeItems(): Flow<List<PantryEntity>>

    @Query("SELECT * FROM pantry_items WHERE id = :id")
    suspend fun getById(id: Long): PantryEntity?

    @Upsert
    suspend fun upsert(item: PantryEntity): Long

    @Delete
    suspend fun delete(item: PantryEntity)
}
