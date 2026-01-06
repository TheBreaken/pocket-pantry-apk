package com.example.pocketpantry.data

import android.content.Context
import com.example.pocketpantry.data.local.PocketPantryDatabase
import com.example.pocketpantry.data.pantry.PantryRepository
import com.example.pocketpantry.data.shopping.ShoppingRepository

class AppContainer(context: Context) {
    private val database: PocketPantryDatabase by lazy {
        PocketPantryDatabase.build(context)
    }

    val pantryRepository: PantryRepository by lazy {
        PantryRepository(database.pantryDao())
    }

    val shoppingRepository: ShoppingRepository by lazy {
        ShoppingRepository(database.shoppingDao())
    }
}
