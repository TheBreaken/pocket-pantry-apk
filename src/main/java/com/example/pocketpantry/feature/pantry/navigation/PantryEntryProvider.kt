package com.example.pocketpantry.feature.pantry.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import com.example.pocketpantry.feature.pantry.ui.PantryEditScreen
import com.example.pocketpantry.feature.pantry.ui.PantryScreen

fun pantryEntryProvider(
    backStack: NavBackStack<NavKey>,
    contentPadding: PaddingValues,
) = entryProvider {
    entry<PantryList> {
        PantryScreen(
            contentPadding = contentPadding,
            onEditItem = { id -> backStack.add(PantryEdit(id = id)) }
        )
    }
    entry<PantryEdit> { key ->
        PantryEditScreen(
            itemId = key.id,
            contentPadding = contentPadding,
            onDone = { backStack.removeLastOrNull() }
        )
    }
}
