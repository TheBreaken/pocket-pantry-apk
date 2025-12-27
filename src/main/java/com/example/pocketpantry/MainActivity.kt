package com.example.pocketpantry

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material.icons.Icons
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Build
import androidx.compose.material3.Icon
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.navigation3.runtime.rememberNavBackStack
import com.example.pocketpantry.feature.pantry.navigation.PantryList
import com.example.pocketpantry.feature.shopping.navigation.ShoppingList
import com.example.pocketpantry.ui.theme.PocketPantryTheme

enum class AppTab {Pantry, Shopping}
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PocketPantryTheme {
                val pantryBackStack = rememberNavBackStack(PantryList)
                val shoppingBackStack = rememberNavBackStack(ShoppingList)

                var currentTab by rememberSaveable() { mutableStateOf(AppTab.Pantry) }
                Log.d("HELLO", "test")
                Scaffold(
                    bottomBar = {
                        NavigationBar {
                            NavigationBarItem(
                                selected = currentTab == AppTab.Pantry,
                                onClick = { currentTab = AppTab.Pantry },
                                icon = { Icon(Icons.Default.Build, "Pantry") },
                                label = { Text("Pantry") }
                            )
                            NavigationBarItem(
                                selected = currentTab == AppTab.Shopping,
                                onClick = { currentTab = AppTab.Shopping },
                                icon = { Icon(Icons.Default.ShoppingCart, "Shopping") },
                                label = { Text("Shopping") }
                            )
                        }
                    }
                ) { padding -> AppNavHost(
                    pantryBackStack = pantryBackStack,
                    shoppingBackStack = shoppingBackStack,
                    currentTab = currentTab,
                    onTabChange = { currentTab = it },
                    contentPadding = padding
                )}
            }
        }
    }
}