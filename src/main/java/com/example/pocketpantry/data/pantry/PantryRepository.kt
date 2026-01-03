package com.example.pocketpantry.data.pantry

import com.example.pocketpantry.data.model.PantryItem
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class PantryRepository(
    private val pantryDao: PantryDao,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    val items: Flow<List<PantryItem>> = pantryDao.observeItems()
        .map { entities -> entities.map { it.toDomain() } }
        .flowOn(dispatcher)

    suspend fun getItem(id: Long): PantryItem? = withContext(dispatcher) {
        pantryDao.getById(id)?.toDomain()
    }

    suspend fun save(item: PantryItem) = withContext(dispatcher) {
        pantryDao.upsert(item.toEntity())
    }

    suspend fun delete(id: Long) = withContext(dispatcher) {
        pantryDao.getById(id)?.let { pantryDao.delete(it) }
    }
}
