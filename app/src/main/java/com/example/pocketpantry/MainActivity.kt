package com.example.pocketpantry

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Camera
import androidx.compose.material.icons.filled.Scanner
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.rememberNavBackStack
import com.example.pocketpantry.feature.pantry.navigation.BarcodeScanner
import com.example.pocketpantry.feature.pantry.navigation.PantryList
import com.example.pocketpantry.feature.pantry.ui.utilities.PantryEditActionButton
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

                Scaffold(
                    floatingActionButton = {
                        PantryEditActionButton(
                            pantryBackStack,
                            visible = currentTab == AppTab.Pantry,
                        )
                    },
                    bottomBar = {
                        NavigationBar {
                            NavigationBarItem(
                                selected = currentTab == AppTab.Pantry,
                                onClick = {
                                    if (currentTab == AppTab.Pantry) {
                                        pantryBackStack.clear()
                                        pantryBackStack.add(PantryList)
                                    } else {
                                        currentTab = AppTab.Pantry
                                    }
                                },
                                icon = { Icon(Icons.Default.Build, "Pantry") },
                                label = { Text("Pantry") }
                            )
                            Box(
                                modifier = Modifier.weight(1f),
                                contentAlignment = Alignment.Center,
                            ) {
                                FloatingActionButton(
                                    onClick = {
                                        pantryBackStack.add(BarcodeScanner)
                                    },
                                    containerColor = MaterialTheme.colorScheme.primary,
                                    shape = CircleShape,
                                    elevation =  FloatingActionButtonDefaults.bottomAppBarFabElevation()
                                ) {
                                    Icon(
                                        Icons.Filled.Camera,
                                        "Barcode scanner",
                                        modifier = Modifier.size(26.dp)
                                    )
                                }
                            }
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