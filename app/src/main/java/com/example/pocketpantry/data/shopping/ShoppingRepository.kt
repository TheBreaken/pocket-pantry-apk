package com.example.pocketpantry.data.shopping

import com.example.pocketpantry.data.model.ShoppingItem
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class ShoppingRepository(
    private val shoppingDao: ShoppingDao,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    val items: Flow<List<ShoppingItem>> = shoppingDao.observeItems()
        .map { entities -> entities.map { it.toDomain() } }
        .flowOn(dispatcher)

    suspend fun save(item: ShoppingItem) = withContext(dispatcher) {
        shoppingDao.upsert(item.toEntity())
    }

    suspend fun setChecked(id: Long, checked: Boolean) = withContext(dispatcher) {
        shoppingDao.updateChecked(id, checked)
    }

    suspend fun delete(id: Long) = withContext(dispatcher) {
        shoppingDao.delete(id)
    }
}
