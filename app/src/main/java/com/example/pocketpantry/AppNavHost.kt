package com.example.pocketpantry

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.*

import androidx.navigation3.ui.NavDisplay
import com.example.pocketpantry.feature.pantry.navigation.PantryList
import com.example.pocketpantry.feature.pantry.navigation.pantryEntryProvider
import com.example.pocketpantry.feature.shopping.navigation.ShoppingList
import com.example.pocketpantry.feature.shopping.navigation.shoppingEntryProvider

@Composable
fun AppNavHost(
    pantryBackStack: NavBackStack<NavKey>,
    shoppingBackStack: NavBackStack<NavKey>,
    currentTab: AppTab,
    onTabChange: (AppTab) -> Unit,
    contentPadding: PaddingValues
) {
    val pantryEntries = pantryTabEntries(pantryBackStack, contentPadding)
    val shoppingEntries = shoppingTabEntries(shoppingBackStack, contentPadding)

    val entries = when (currentTab) {
        AppTab.Pantry -> pantryEntries
        AppTab.Shopping -> shoppingEntries
    }

    NavDisplay(
        entries = entries,
        onBack = {
            val activeStack = when (currentTab) {
                AppTab.Pantry -> pantryBackStack
                AppTab.Shopping -> shoppingBackStack
            }

            activeStack.removeLastOrNull()

            // Optional UX: if user hits back at root of Shopping, switch to Pantry
            if (currentTab == AppTab.Shopping && activeStack.isEmpty()) {
                onTabChange(AppTab.Pantry)
            }
        }
    )
}

@Composable
private fun pantryTabEntries(backStack: NavBackStack<NavKey>, contentPadding: PaddingValues): List<NavEntry<NavKey>> {
    if (backStack.isEmpty()) backStack.add(PantryList)

    val decorators: List<NavEntryDecorator<NavKey>> = listOf(
        rememberSaveableStateHolderNavEntryDecorator(),
        rememberViewModelStoreNavEntryDecorator()
    )

    val provider = pantryEntryProvider(backStack, contentPadding)

    return rememberDecoratedNavEntries(backStack, decorators, provider)
}

@Composable
private fun shoppingTabEntries(backStack: NavBackStack<NavKey>, contentPadding: PaddingValues): List<NavEntry<NavKey>> {
    if (backStack.isEmpty()) backStack.add(ShoppingList)

    val decorators: List<NavEntryDecorator<NavKey>> = listOf(
        rememberSaveableStateHolderNavEntryDecorator(),
        rememberViewModelStoreNavEntryDecorator()
    )

    val provider = shoppingEntryProvider(contentPadding)

    return rememberDecoratedNavEntries(backStack, decorators, provider)
}