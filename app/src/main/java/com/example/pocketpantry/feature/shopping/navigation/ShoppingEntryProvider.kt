package com.example.pocketpantry.feature.shopping.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import com.example.pocketpantry.feature.shopping.ui.ShoppingScreen

fun shoppingEntryProvider(
    contentPadding: PaddingValues
) = entryProvider<NavKey> {
    entry<ShoppingList> {
        ShoppingScreen(
            contentPadding = contentPadding
        )
    }
}